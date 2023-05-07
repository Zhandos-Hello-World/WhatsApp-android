package kz.tinkoff.homework_2.presentation.message

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.coreui.custom.viewgroup.CustomMessageTextFieldBar
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.databinding.FragmentMessageBinding
import kz.tinkoff.homework_2.di_dagger.message.DaggerMessageComponent
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageDataModule
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageNetworkModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.delegates.date.DateDelegate
import kz.tinkoff.homework_2.presentation.delegates.message.MessageAdapterListener
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegate
import kz.tinkoff.homework_2.presentation.delegates.message.MessageScrollControllerListener
import kz.tinkoff.homework_2.presentation.message.elm.MessageEffect
import kz.tinkoff.homework_2.presentation.message.elm.MessageEvent
import kz.tinkoff.homework_2.presentation.message.elm.MessageState
import kz.tinkoff.homework_2.presentation.message.elm.MessageStoreFactory
import kz.tinkoff.homework_2.presentation.reaction.ReactionBottomSheetDialog
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder

class MessageFragment(private val args: MessageArgs) :
    ElmFragment<MessageEvent, MessageEffect, MessageState>(), MessageAdapterListener {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainAdapter by lazyUnsafe {
        MainAdapter().apply {
            addDelegate(DateDelegate() as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
            addDelegate(MessageDelegate(listener = this@MessageFragment) as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
        }
    }

    override val initEvent: MessageEvent = MessageEvent.Ui.LoadMessages(args)

    @Inject
    lateinit var messageStoreFactory: MessageStoreFactory

    override val storeHolder by lazyUnsafe {
        LifecycleAwareStoreHolder(lifecycle) {
            messageStoreFactory.provide()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerMessageComponent.builder()
            .messageDataModule(MessageDataModule())
            .messageNetworkModule(MessageNetworkModule())
            .appComponent(requireContext().getAppComponent())
            .build().inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        configureHeader(args)

        binding.apply {
            binding.messageRecycler.adapter = adapter
            binding.messageRecycler.addOnScrollListener(
                MessageScrollControllerListener {
                    //store.accept(MessageEvent.Ui.ItemShowed(it))
                }
            )

            messageSendEditTextBar.setOnSendClickListener {
                if (it == CustomMessageTextFieldBar.SendMessageState.SEND_MESSAGE) {
                    val message = messageSendEditTextBar.getText().toString()
                    store.accept(MessageEvent.Ui.AddMessage(message))
                    messageSendEditTextBar.clearText()
                }
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as? BottomBarController)?.showBottomNavigationView(false)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as? BottomBarController)?.showBottomNavigationView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun render(state: MessageState) {
        hideAll()
        when (state) {
            is MessageState.Data -> {
                binding.apply {
                    adapter.submitList(emptyList())
                    messageRecycler.adapter = null
                    messageRecycler.isVisible = state.messageDvo.isNotEmpty()

                    adapter.submitList(state.messageDvo)
                    messageRecycler.adapter = adapter
                    messageRecycler.scrollToPosition(state.messageDvo.size - 1)
                }
            }
            is MessageState.UpdatedPosition -> {
                binding.apply {
                    messageRecycler.isVisible = true
                    messageRecycler.adapter?.notifyItemChanged(state.position)
                }
            }
            is MessageState.Loading -> {
                binding.apply {
                    loadingState.isVisible = true
                }
            }
            is MessageState.Error -> {
                binding.apply {
                    errorState.isVisible = true
                }
            }
        }
    }

    private fun hideAll() {
        binding.apply {
            messageRecycler.isVisible = false
            loadingState.isVisible = false
            errorState.isVisible = false
        }
    }

    override fun addReactionClickListener(position: Int) {
        val bottomSheetDialogFragment = ReactionBottomSheetDialog()
        bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)

        parentFragmentManager.setFragmentResultListener(
            ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK,
            this
        ) { requestKey, result ->
            val result =
                result.getString(ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK_RESULT)
            result?.let { result ->
                store.accept(MessageEvent.Ui.AddReaction(position, result))
            }
        }
    }

    override fun setEmojiClickListener(position: Int, viewItem: ReactionViewItem) {
        if (viewItem.fromMe) {
            store.accept(MessageEvent.Ui.DeleteReaction(position, viewItem.emoji))
        } else {
            store.accept(MessageEvent.Ui.AddReaction(position, viewItem.emoji))
        }
    }

    private fun configureHeader(args: MessageArgs) {
        binding.apply {
            subToolbar.setText(args.topic)
            toolbar.apply {
                setOnBackClickListener {
                    store.accept(MessageEvent.Ui.BackToChannels)
                }
                setToolbarText(args.stream)
            }
        }
    }
}
