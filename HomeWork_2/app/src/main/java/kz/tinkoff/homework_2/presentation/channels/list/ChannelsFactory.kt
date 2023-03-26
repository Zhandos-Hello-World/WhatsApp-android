package kz.tinkoff.homework_2.presentation.channels.list

import kz.tinkoff.homework_2.presentation.delegates.channels.ChannelModel

interface ChannelsFactory {
    fun createChannelsList(): List<ChannelModel>
}

class DefaultChannelListFactory : ChannelsFactory {

    override fun createChannelsList(): List<ChannelModel> {
        val list = buildList<ChannelModel> {
            for (i in channels.indices) {
                add(ChannelModel(i, channels[i], i + 100, i + 100))
            }
        }
        return list
    }

    companion object {
        private val channels = listOf(
            "#general",
            "#Development",
            "#Design",
            "#PR"
        )
    }
}