package kz.tinkoff.homework_2.presentation.reaction

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.serialization.Serializable
import kz.tinkoff.core.emoji.EmojiNCS
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.DialogBottomSheetReactionsBinding
import kz.tinkoff.homework_2.presentation.reaction.factory.DefaultReactionListFactory
import kz.tinkoff.homework_2.presentation.reaction.factory.ReactionListFactory


class ReactionBottomSheetDialog :
    BottomSheetDialogFragment(R.layout.dialog_bottom_sheet_reactions) {
    private lateinit var binding: DialogBottomSheetReactionsBinding

    private var adapter: ReactionAdapter? = null
    private var factory: ReactionListFactory? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogBottomSheetReactionsBinding.bind(view)

        factory = DefaultReactionListFactory()

        factory?.let {
            setReactionsToAdapter(it.createReactionList())
        }

        binding.deleteBtn.setOnClickListener { backToWithDeleteMessage() }
        binding.copyBtn.setOnClickListener { backToWithCopyClipboard() }
        binding.changeBtn.setOnClickListener { backToWithChange() }
        binding.forwardBtn.setOnClickListener { backToWithForward() }
    }


    private fun setReactionsToAdapter(reactionList: List<EmojiNCS>) {
        adapter = ReactionAdapter(requireContext(), reactionList) {
            val bundle = Bundle()
            bundle.putString(REACTION_BOTTOM_SHEET_CALLBACK_RESULT, it.name)
            parentFragmentManager.setFragmentResult(REACTION_BOTTOM_SHEET_CALLBACK, bundle)
            dismiss()
        }
        binding.reactionGridView.adapter = adapter
    }

    private fun backToWithChange() {
        saveResultToParentFragment(MessageConfigureKey.CHANGE_MESSAGE_CALLBACK_RESULT)
        dismiss()
    }

    private fun backToWithForward() {
        saveResultToParentFragment(MessageConfigureKey.FORWARD_MESSAGE_RESULT)
        dismiss()
    }

    private fun backToWithDeleteMessage() {
        saveResultToParentFragment(MessageConfigureKey.DELETE_MESSAGE_CALLBACK_RESULT)
        dismiss()
    }

    private fun backToWithCopyClipboard() {
        saveResultToParentFragment(MessageConfigureKey.COPY_MESSAGE_CALLBACK_RESULT)
        dismiss()
    }

    private fun saveResultToParentFragment(result: MessageConfigureKey) {
        val bundle = Bundle()
        bundle.putSerializable(BUTTON_CLICKED_RESULT, result)
        parentFragmentManager.setFragmentResult(REACTION_BOTTOM_SHEET_CALLBACK, bundle)
    }


    companion object {
        const val REACTION_BOTTOM_SHEET_CALLBACK = "REACTION_BOTTOM_SHEET_CALLBACK"
        const val REACTION_BOTTOM_SHEET_CALLBACK_RESULT = "RESULT"
        const val BUTTON_CLICKED_RESULT = "BUTTON_CLICKED_RESULT"
    }
}

@Serializable
enum class MessageConfigureKey(val type: String) {
    DELETE_MESSAGE_CALLBACK_RESULT("DELETE_MESSAGE_RESULT"),
    COPY_MESSAGE_CALLBACK_RESULT("COPY_MESSAGE_CALLBACK_RESULT"),
    CHANGE_MESSAGE_CALLBACK_RESULT("CHANGE_MESSAGE_CALLBACK_RESULT"),
    FORWARD_MESSAGE_RESULT("FORWARD_MESSAGE_RESULT")
}
