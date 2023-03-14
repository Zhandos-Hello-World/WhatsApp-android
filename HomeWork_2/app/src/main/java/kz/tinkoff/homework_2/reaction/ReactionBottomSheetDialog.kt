package kz.tinkoff.homework_2.reaction

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.DialogBottomSheetReactionsBinding
import kz.tinkoff.homework_2.reaction.factory.DefaultReactionListFactory
import kz.tinkoff.homework_2.reaction.factory.ReactionListFactory


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
    }


    private fun setReactionsToAdapter(reactionList: List<String>) {
        adapter = ReactionAdapter(requireContext(), reactionList) {
            val bundle = Bundle()
            bundle.putString(REACTION_BOTTOM_SHEET_CALLBACK_RESULT, it)
            parentFragmentManager.setFragmentResult(REACTION_BOTTOM_SHEET_CALLBACK, bundle)
            dismiss()
        }
        binding.reactionGridView.adapter = adapter
    }


    companion object {
        const val REACTION_BOTTOM_SHEET_CALLBACK = "REACTION_BOTTOM_SHEET_CALLBACK"
        const val REACTION_BOTTOM_SHEET_CALLBACK_RESULT = "RESULT"
    }
}