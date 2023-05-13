package kz.tinkoff.homework_2.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.tinkoff.homework_2.presentation.create_stream.CreateStreamFragment
import kz.tinkoff.homework_2.presentation.create_topic.CreateTopicFragment
import kz.tinkoff.homework_2.presentation.dvo.StreamDvo
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.message.MessageFragment
import kz.tinkoff.homework_2.presentation.people.PeopleFragment
import kz.tinkoff.homework_2.presentation.profile.ProfileFragment
import kz.tinkoff.homework_2.presentation.streams.StreamsContainerFragment

object Screens {
    fun StreamsScreen() = FragmentScreen { StreamsContainerFragment() }
    fun ProfileScreen() = FragmentScreen { ProfileFragment() }
    fun PeopleScreen() = FragmentScreen { PeopleFragment() }


    fun MessageScreen(args: MessageArgs) =
        FragmentScreen("Message_${args.hashCode()}") { MessageFragment(args) }

    fun CreateStreamScreen() = FragmentScreen { CreateStreamFragment() }
    fun CreateTopicScreen(args: StreamDvo) =
        FragmentScreen("StreamDvo_${args.hashCode()}") { CreateTopicFragment(args) }
}
