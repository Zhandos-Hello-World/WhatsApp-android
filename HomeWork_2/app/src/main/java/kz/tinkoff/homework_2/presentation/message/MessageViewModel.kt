package kz.tinkoff.homework_2.presentation.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import kz.tinkoff.core.adapter.DelegateItem
import kz.tinkoff.coreui.item.ReactionViewItem
import kz.tinkoff.homework_2.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.navigation.Screens
import kz.tinkoff.homework_2.presentation.delegates.message.MessageDelegateItem
import kz.tinkoff.homework_2.presentation.delegates.message.MessageModel

class MessageViewModel(
    private val messageDvoMapper: MessageDvoMapper,
    private val router: Router,
): ViewModel() {
    private val _messageList = MutableLiveData<List<DelegateItem>>()
    val messageList: LiveData<List<DelegateItem>> = _messageList

    init {
        _messageList.value = messageDvoMapper.toMessageWithData(messageModelList)
    }

    fun updateDelegate(model: MessageModel, emoji: String) {
        val delegateItem = findMessageDelegateItem(model)
        val reactions = model.reactions.toMutableList()

        if (containsEmoji(model, emoji)) {
            val reactionViewItem = reactions.find { it.emoji == emoji }!!
            if (reactionViewItem.fromMe) {
                changeReactions(reactions, reactionViewItem)
            } else {
                addReactionToExist(reactions, reactionViewItem)
            }
        } else {
            addReaction(reactions, emoji)
        }
        delegateItem ?: return
        updateMessageModel(model, delegateItem, reactions)
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

        _messageList.value = _messageList.value?.toMutableList()?.apply {
            add(delegateItem)
        }
    }

    fun backToChannels() {
        router.backTo(Screens.ChannelsScreen())
    }

    private fun updateMessageModel(
        model: MessageModel,
        delegateItem: DelegateItem,
        reactions: MutableList<ReactionViewItem>,
    ) {
        model.reactions = reactions
        val newDelegateItem = MessageDelegateItem(
            delegateItem.hashCode(),
            model
        )
        val list = _messageList.value
        val mutableList = list?.toMutableList()
        mutableList?.set(list.indexOf(delegateItem), newDelegateItem)
        _messageList.value = mutableList
    }

    private fun findMessageDelegateItem(model: MessageModel): DelegateItem? {
        return _messageList.value?.find { delegateItem ->
            if (delegateItem.content() is MessageModel) {
                return@find (delegateItem.content() as MessageModel).id == model.id
            }
            false
        }
    }

    private fun containsEmoji(model: MessageModel, emoji: String): Boolean {
        val reactions = model.reactions.toMutableList()
        return reactions.map { it.emoji }.contains(emoji)
    }

    private fun changeReactions(
        reactions: MutableList<ReactionViewItem>,
        reactionViewItem: ReactionViewItem,
    ): MutableList<ReactionViewItem> {
        val index = reactions.indexOf(reactionViewItem)
        val newReactionViewItem = reactionViewItem.copy(count = reactionViewItem.count - 1)
        if (newReactionViewItem.count == 0) {
            reactions.removeAt(index)
        } else {
            reactions[index] =
                reactionViewItem.copy(count = reactionViewItem.count - 1, fromMe = false)
        }
        return reactions
    }

    private fun addReactionToExist(
        reactions: MutableList<ReactionViewItem>,
        reactionViewItem: ReactionViewItem,
    ) {
        val index = reactions.indexOf(reactionViewItem)
        reactions[index] =
            reactionViewItem.copy(count = reactionViewItem.count + 1, fromMe = true)
    }

    private fun addReaction(
        reactions: MutableList<ReactionViewItem>,
        emoji: String,
    ) {
        reactions.add(ReactionViewItem(hashCode(), 1, emoji, fromMe = true))
    }

    companion object {
        private const val JUL_5 = "5 июля"
        private const val SEP_1 = "1 сенятбря"
        private const val SEP_12 = "12 сенятбря"
        private const val DEC_7 = "7 декабря"

        private val messageModelList = listOf(
            MessageModel(
                id = 123,
                fullName = "Baimurat Zhandos",
                message = " jiroewj giorejg ierjg er1 ioew fjioew jfor jgiowerj groe jgoriwe",
                date = SEP_1,
                reactions = listOf(
                    ReactionViewItem(0, 1, "🔥")
                )
            ),
            MessageModel(
                id = 124,
                fullName = "Baimurat Zhandos",
                message = "2 jgerio gjeroj girwej goer fjoeiwjf e fewjio ef iewojf ioew",
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
                message = " jqio fjiroej girej  ejof jweop fwep fewp wer7",
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