package kz.tinkoff.homework_2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.delegates.date.DateModel
import kz.tinkoff.homework_2.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.delegates.message.MessageModel
import kz.tinkoff.homework_2.delegates.utils.concatenateWithDate

class MainViewModel: ViewModel() {
    var messageList = MutableLiveData<List<DelegateItem>>()


    init {
        messageList.value = messageModelList.concatenateWithDate(stubDatesList)
    }

    fun updateDelegate(model: MessageModel, emoji: String) {
        val list = messageList.value
        val delegateItem = list?.find { delegateItem ->
            if (delegateItem is MessageDelegateItem) {
                return@find (delegateItem.content() as MessageModel).id == model.id
            }
            false
        }
        val reactions = model.reactions.toMutableList()
        val contains = reactions.map { it.emoji }.contains(emoji)

        if (contains) {
            val reactionViewItem = reactions.find { it.emoji == emoji }!!
            val index = reactions.indexOf(reactionViewItem)

            if (reactionViewItem.fromMe) {
                val newReactionViewItem = reactionViewItem.copy(count = reactionViewItem.count - 1)
                if (newReactionViewItem.count == 0) {
                    reactions.removeAt(index)
                }
                else {
                    reactions[index] = reactionViewItem.copy(count = reactionViewItem.count - 1, fromMe = false)
                }
            }
            else {
                reactions[index] = reactionViewItem.copy(count = reactionViewItem.count + 1, fromMe = true)
            }
        } else {
            reactions.add(ReactionViewItem(hashCode(), 1, emoji, fromMe = true))
        }
        model.reactions = reactions
        val newDelegateItem = MessageDelegateItem(
            delegateItem?.hashCode() ?: 0,
            model
        )
        val mutableList = list?.toMutableList()
        mutableList?.set(list.indexOf(delegateItem), newDelegateItem)
        messageList.value = mutableList
    }

    fun addMessage(message: String) {
        val delegateItem = MessageDelegateItem(
            id = hashCode(),
            value = MessageModel(
                id = hashCode(),
                fullName = "Baimurat Zhandos",
                message = message,
                date = DEC_7,
                reactions = emptyList()
            )
        )

        messageList.value = messageList.value?.toMutableList()?.apply {
            add(delegateItem)
        }
    }

    companion object {
        private const val JUL_5 = "5 июля"
        private const val SEP_1 = "1 сенятбря"
        private const val SEP_12 = "12 сенятбря"
        private const val DEC_7 = "7 декабря"


        private val stubDatesList = listOf(
            DateModel(
                id = 1,
                date = SEP_1,
            ),
            DateModel(
                id = 2,
                date = SEP_12,
            ),
            DateModel(
                id = 3,
                date = JUL_5,
            ),
            DateModel(
                id = 4,
                date = DEC_7,
            ),
        )

        private val messageModelList = listOf(
            MessageModel(
                id = 123,
                fullName = "Baimurat Zhandos",
                message = " jiroewj giorejg ierjg er1",
                date = SEP_1,
                reactions = listOf(
                    ReactionViewItem(0, 1, "🔥")
                )
            ),
            MessageModel(
                id = 124,
                fullName = "Baimurat Zhandos",
                message = "2 jgerio gjeroj girwej goer",
                date = SEP_1,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 125,
                fullName = "Baimurat Zhandos",
                message = "3 qerjigo jerwig jwer",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 126,
                fullName = "Diana",
                message = " rejg ioejrg ioerj 4",
                date = JUL_5,
                reactions = mutableListOf(),
            ),
            MessageModel(
                id = 127,
                fullName = "Baimurat Zhandos",
                message = "5 rjeoi gjiewr jgioer gwrjeio",
                date = DEC_7,
                reactions = listOf(
                    ReactionViewItem(0, 1, "👍")
                ),
            ),
            MessageModel(
                id = 128,
                fullName = "Diana",
                message = " rjeigo jreiog jrieoj geir6",
                date = DEC_7,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 129,
                fullName = "Baimurat Zhandos",
                message = " jqio fjiroej girej r7",
                date = DEC_7,
                reactions = listOf(
                    ReactionViewItem(0, 1, "🔥")
                ),
            ),
            MessageModel(
                id = 130,
                fullName = "Diana",
                message = " jer giorewj giorewjg 8",
                date = DEC_7,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 131,
                fullName = "Baimurat Zhandos",
                message = "9 ejriog jerio gjirewj gower",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 132,
                fullName = "Diana",
                message = "10 jrejg ioerjg oierj",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 133,
                fullName = "Baimurat Zhandos",
                message = "1 jgiorqej giorqj gioeq1",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 134,
                fullName = "Baimurat Zhandos",
                message = "1 jqgrioeg jioerwj g2",
                date = DEC_7,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 135,
                fullName = "Diana",
                message = "1 grioejg iorewj iore3",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 136,
                fullName = "Baimurat Zhandos",
                message = "14tjhoiwertjhipwetjhi",
                date = SEP_12,
                reactions = emptyList(),
            ),
            MessageModel(
                id = 137,
                fullName = "Baimurat Zhandos",
                message = "1giwerojgiwopejhtwe5",
                date = DEC_7,
                reactions = emptyList(),
            ),
        )
    }
}