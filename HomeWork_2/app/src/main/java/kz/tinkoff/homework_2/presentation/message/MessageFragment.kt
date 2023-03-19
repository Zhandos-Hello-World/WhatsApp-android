package kz.tinkoff.homework_2.presentation.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Router
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.coreui.BottomBarController
import kz.tinkoff.coreui.custom.viewgroup.CustomMessageTextFieldBar
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.databinding.FragmentMessageBinding
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.date.DateDelegate
import kz.tinkoff.homework_2.presentation.delegates.message.MessageAdapterListener
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegate
import kz.tinkoff.homework_2.presentation.delegates.message.MessageModel
import kz.tinkoff.homework_2.presentation.reaction.ReactionBottomSheetDialog
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageFragment : Fragment(), MessageAdapterListener {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageViewModel by viewModel()
    private val adapter: MainAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MainAdapter().apply {
            addDelegate(DateDelegate() as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
            addDelegate(MessageDelegate(listener = this@MessageFragment) as AdapterDelegate<RecyclerView.ViewHolder, DelegateItem>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        binding.messageRecycler.adapter = adapter

        viewModel.messageList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.messageSendEditTextBar.setOnSendClickListener {
            if (it == CustomMessageTextFieldBar.SendMessageState.SEND_MESSAGE) {
                val message = binding.messageSendEditTextBar.getText().toString()
                viewModel.addMessage(message)
                binding.messageSendEditTextBar.clearText()
            }
        }
        binding.toolbar.setOnBackClickListener {
            viewModel.backToChannels()
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

    override fun addReactionClickListener(model: MessageModel) {
        val bottomSheetDialogFragment = ReactionBottomSheetDialog()
        bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)

        parentFragmentManager.setFragmentResultListener(
            ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK,
            this
        ) { requestKey, result ->
            val result =
                result.getString(ReactionBottomSheetDialog.REACTION_BOTTOM_SHEET_CALLBACK_RESULT)
            result?.let { result ->
                viewModel.updateDelegate(model, result)
            }
        }
    }

    override fun setEmojiClickListener(model: MessageModel, viewItem: ReactionViewItem) {
        viewModel.updateDelegate(model, viewItem.emoji)
    }
}