package kz.tinkoff.homework_2.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.tinkoff.homework_2.presentation.channels.ChannelsFragment
import kz.tinkoff.homework_2.presentation.message.MessageArgs
import kz.tinkoff.homework_2.presentation.message.MessageFragment
import kz.tinkoff.homework_2.presentation.people.PeopleFragment
import kz.tinkoff.homework_2.presentation.profile.ProfileFragment

object Screens {
    fun ChannelsScreen() = FragmentScreen { ChannelsFragment() }
    fun ProfileScreen() = FragmentScreen { ProfileFragment() }
    fun PeopleScreen() = FragmentScreen { PeopleFragment() }


    fun MessageScreen(args: MessageArgs) = FragmentScreen("Message_${args.hashCode()}") { MessageFragment(args) }
}