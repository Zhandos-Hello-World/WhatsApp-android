package kz.tinkoff.homework_2.reaction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.tinkoff.homework_2.R
import kz.tinkoff.homework_2.databinding.DialogBottomSheetReactionsBinding


class ReactionBottomSheetDialog: BottomSheetDialogFragment(R.layout.dialog_bottom_sheet_reactions) {
    private lateinit var binding: DialogBottomSheetReactionsBinding

    private var adapter: ReactionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogBottomSheetReactionsBinding.bind(view)

        adapter = ReactionAdapter(requireContext(), getEmojiList()) {
            val bundle = Bundle()
            bundle.putString(REACTION_BOTTOM_SHEET_CALLBACK_RESULT, it)
            parentFragmentManager.setFragmentResult(REACTION_BOTTOM_SHEET_CALLBACK, bundle)
            dismiss()
        }
        binding.reactionGridView.adapter = adapter


    }


    private fun getEmojiList(): List<String> {
        val emojiList: MutableList<String> = ArrayList()
        emojiList.add("\uD83D\uDE00")
        emojiList.add("\uD83D\uDE01")
        emojiList.add("\uD83D\uDE02")
        emojiList.add("\uD83D\uDE03")
        emojiList.add("\uD83D\uDE04")
        emojiList.add("\uD83D\uDE05")
        emojiList.add("\uD83D\uDE06")
        emojiList.add("\uD83D\uDE07")
        emojiList.add("\uD83D\uDE08")
        emojiList.add("\uD83D\uDE09")
        emojiList.add("\uD83D\uDE0A")
        emojiList.add("\uD83D\uDE0B")
        emojiList.add("\uD83D\uDE0C")
        emojiList.add("\uD83D\uDE0D")
        emojiList.add("\uD83D\uDE0E")
        emojiList.add("\uD83D\uDE0F")
        emojiList.add("\uD83D\uDE10")
        emojiList.add("\uD83D\uDE11")
        emojiList.add("\uD83D\uDE12")
        emojiList.add("\uD83D\uDE13")
        emojiList.add("\uD83D\uDE14")
        emojiList.add("\uD83D\uDE15")
        emojiList.add("\uD83D\uDE16")
        emojiList.add("\uD83D\uDE17")
        emojiList.add("\uD83D\uDE18")
        emojiList.add("\uD83D\uDE19")
        emojiList.add("\uD83D\uDE1A")
        emojiList.add("\uD83D\uDE1B")
        emojiList.add("\uD83D\uDE1C")
        emojiList.add("\uD83D\uDE1D")
        emojiList.add("\uD83D\uDE1E")
        emojiList.add("\uD83D\uDE1F")
        emojiList.add("\uD83D\uDE20")
        emojiList.add("\uD83D\uDE21")
        emojiList.add("\uD83D\uDE22")
        emojiList.add("\uD83D\uDE23")
        emojiList.add("\uD83D\uDE24")
        emojiList.add("\uD83D\uDE25")
        emojiList.add("\uD83D\uDE26")
        emojiList.add("\uD83D\uDE27")
        emojiList.add("\uD83D\uDE28")
        emojiList.add("\uD83D\uDE29")
        emojiList.add("\uD83D\uDE2A")
        emojiList.add("\uD83D\uDE2B")
        emojiList.add("\uD83D\uDE2C")
        emojiList.add("\uD83D\uDE2D")
        emojiList.add("\uD83D\uDE2E")
        emojiList.add("\uD83D\uDE2F")
        emojiList.add("\uD83D\uDE30")
        emojiList.add("\uD83D\uDE31")
        emojiList.add("\uD83D\uDE32")
        emojiList.add("\uD83D\uDE33")
        emojiList.add("\uD83D\uDE34")
        emojiList.add("\uD83D\uDE35")
        emojiList.add("\uD83D\uDE36")
        emojiList.add("\uD83D\uDE37")
        emojiList.add("\uD83D\uDE38")
        emojiList.add("\uD83D\uDE39")
        emojiList.add("\uD83D\uDE3A")
        emojiList.add("\uD83D\uDE3B")
        emojiList.add("\uD83D\uDE3C")
        emojiList.add("\uD83D\uDE3D")
        emojiList.add("\uD83D\uDE3E")
        emojiList.add("\uD83D\uDE3F")

        return emojiList
    }

    companion object {
        const val REACTION_BOTTOM_SHEET_CALLBACK = "REACTION_BOTTOM_SHEET_CALLBACK"
        const val REACTION_BOTTOM_SHEET_CALLBACK_RESULT = "RESULT"
    }
}