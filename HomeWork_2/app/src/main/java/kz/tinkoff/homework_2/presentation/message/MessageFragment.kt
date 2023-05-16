package kz.tinkoff.homework_2.presentation.message

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.core.utils.lazyUnsafe
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.coreui.custom.viewgroup.CustomMessageTextFieldBar.SendMessageState
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.FragmentMessageBinding
import kz.tinkoff.homework_2.di_dagger.message.DaggerMessageComponent
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageDataModule
import kz.tinkoff.homework_2.di_dagger.message.modules.MessageNetworkModule
import kz.tinkoff.homework_2.getAppComponent
import kz.tinkoff.homework_2.presentation.delegates.date.DateDelegate
import kz.tinkoff.homework_2.presentation.delegates.message.MessageAdapterListener
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegate
import kz.tinkoff.homework_2.presentation.message.elm.MessageEffect
import kz.tinkoff.homework_2.presentation.message.elm.MessageEvent
import kz.tinkoff.homework_2.presentation.message.elm.MessageState
import kz.tinkoff.homework_2.presentation.message.elm.MessageStoreFactory
import kz.tinkoff.homework_2.presentation.reaction.MessageConfigureKey
import kz.tinkoff.homework_2.presentation.reaction.ReactionBottomSheetDialog
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment.Companion.SELECTED_STREAM_ID
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment.Companion.SELECTED_TOPIC_NAME
import kz.tinkoff.homework_2.presentation.streams.list.StreamsListFragment.Companion.STREAM_LIST_SELECT_TOPIC_RESULT
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
            messageRecycler.adapter = adapter
            messageSendEditTextBar.setState(SendMessageState.SendMessage)

            messageSendEditTextBar.setOnSendClickListener { state ->
                setMessageSendListener(state)
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
                    messageRecycler.isVisible = state.messageDvo.isNotEmpty()
                    adapter.submitList(state.messageDvo)
                    if (state.messageDvo.isNotEmpty()) {
                        messageRecycler.smoothScrollToPosition(state.messageDvo.size - 1)
                    }
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

    override fun changeMessageClickListener(position: Int) {
        val bottomSheetDialogFragment = ReactionBottomSheetDialog()
        bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)

        parentFragmentManager.setFragmentResultListener(
            ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK,
            this
        ) { _, result ->
            result.getString(ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK_RESULT)
                ?.let {
                    store.accept(MessageEvent.Ui.AddReaction(position, it))
                }

            val messageConfigureKey = getMessageConfigureClickResult(result)
            if (messageConfigureKey != null) {
                onReactionBottomResultListener(position, messageConfigureKey)
            }
        }
    }

    private fun getMessageConfigureClickResult(bundle: Bundle): MessageConfigureKey? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getSerializable(
                ReactionBottomSheetDialog.BUTTON_CLICKED_RESULT,
                MessageConfigureKey::class.java
            )
        } else {
            (bundle.getSerializable(ReactionBottomSheetDialog.BUTTON_CLICKED_RESULT) as? MessageConfigureKey)
        }
    }

    private fun onReactionBottomResultListener(
        position: Int,
        key: MessageConfigureKey,
    ) {
        when (key) {
            MessageConfigureKey.DELETE_MESSAGE_CALLBACK_RESULT -> {
                store.accept(MessageEvent.Ui.DeleteMessage(position))
            }
            MessageConfigureKey.COPY_MESSAGE_CALLBACK_RESULT -> {
                store.accept(MessageEvent.Ui.CopyToClipBoardEvent(position))
            }
            MessageConfigureKey.CHANGE_MESSAGE_CALLBACK_RESULT -> {
                store.accept(MessageEvent.Ui.RequestToChangeMessageContentEvent(position))
            }
            MessageConfigureKey.FORWARD_MESSAGE_RESULT -> {
                store.accept(MessageEvent.Ui.SelectTopicEvent)
                parentFragmentManager.setFragmentResultListener(
                    STREAM_LIST_SELECT_TOPIC_RESULT,
                    this
                ) { _, result ->
                    store.accept(
                        MessageEvent.Ui.ForwardMessageToTopicEvent(
                            position = position,
                            topicName = result.getString(SELECTED_TOPIC_NAME, ""),
                            streamId = result.getInt(SELECTED_STREAM_ID)
                        )
                    )
                }
            }
        }
    }

    override fun handleEffect(effect: MessageEffect) {
        return when (effect) {
            is MessageEffect.CopyToClipBoardEffect -> {
                copyToClipboard(effect.text)
                Toast.makeText(requireContext(), R.string.copied, Toast.LENGTH_SHORT).show()
            }
            is MessageEffect.MessageForwardToTopicEffect -> {
                Toast.makeText(
                    requireContext(),
                    R.string.forwarded_successfully,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is MessageEffect.MessageChangeEffect -> {
                showChangeMessagedBanner(effect.position, effect.content)
            }
            is MessageEffect.ShowToastEffect -> {
                Toast.makeText(requireContext(), effect.id, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showChangeMessagedBanner(position: Int, content: String) {
        binding.apply {
            messageSendEditTextBar.setState(
                SendMessageState.ChangeMessage(position, content)
            )
            changeTextBanner.isVisible = true
            changeTextBanner.text = content

            changeTextBanner.setOnClickListener {
                messageSendEditTextBar.setState(
                    SendMessageState.SendMessage
                )
                changeTextBanner.isVisible = false
            }
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(requireContext(), ClipboardManager::class.java)
        val clipData = ClipData.newPlainText("Copied Text", text)
        clipboardManager?.setPrimaryClip(clipData)
    }

    private fun setMessageSendListener(state: SendMessageState) {
        when (state) {
            is SendMessageState.SendMessage -> {
                val message = binding.messageSendEditTextBar.getText().toString()
                store.accept(MessageEvent.Ui.AddMessage(message))
                binding.messageSendEditTextBar.clearText()
            }
            is SendMessageState.SendOther -> {

            }
            is SendMessageState.ChangeMessage -> {
                binding.apply {
                    val changeMessage = messageSendEditTextBar.getText().toString()
                    store.accept(
                        MessageEvent.Ui.ChangeMessageContentEvent(
                            state.position,
                            changeMessage
                        )
                    )
                    messageSendEditTextBar.clearText()
                    changeTextBanner.isVisible = false
                    messageSendEditTextBar.setState(SendMessageState.SendMessage)
                }
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
                    store.accept(MessageEvent.Ui.BackToStreams)
                }
                setToolbarText(args.stream)
            }
        }
    }
}
