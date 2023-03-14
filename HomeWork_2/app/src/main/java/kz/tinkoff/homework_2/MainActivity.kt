package kz.tinkoff.homework_2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kz.tinkoff.core.adapter.AdapterDelegate
import kz.tinkoff.core.adapter.MainAdapter
import kz.tinkoff.coreui.custom.viewgroup.CustomMessageTextFieldBar
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.databinding.ActivityMainBinding
import kz.tinkoff.homework_2.delegates.date.DateDelegate
import kz.tinkoff.homework_2.delegates.message.MessageAdapterListener
import kz.tinkoff.homework_2.delegates.message.MessageDelegate
import kz.tinkoff.homework_2.delegates.message.MessageModel
import kz.tinkoff.homework_2.reaction.ReactionBottomSheetDialog

class MainActivity : AppCompatActivity(), MessageAdapterListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val adapter: MainAdapter by lazy { MainAdapter() }
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        adapter.apply {
            addDelegate(DateDelegate() as AdapterDelegate<ViewHolder, Any>)
            addDelegate(MessageDelegate(listener = this@MainActivity) as AdapterDelegate<ViewHolder, Any>)
        }

        binding.messageRecycler.adapter = adapter

        viewModel.messageList.observe(this) {
            adapter.submitList(it)
        }

        binding.messageSendEditTextBar.setOnSendClickListener {
            if (it == CustomMessageTextFieldBar.SendMessageState.SEND_MESSAGE) {
                val message = binding.messageSendEditTextBar.getText().toString()
                viewModel.addMessage(message)
                binding.messageSendEditTextBar.clearText()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun addReactionClickListener(model: MessageModel) {
        val bottomSheetDialogFragment = ReactionBottomSheetDialog()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)

        supportFragmentManager.setFragmentResultListener(
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