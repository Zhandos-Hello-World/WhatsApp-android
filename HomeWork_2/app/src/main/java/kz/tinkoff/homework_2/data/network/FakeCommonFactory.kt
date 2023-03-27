package kz.tinkoff.homework_2.data.network

import java.util.Locale
import kz.tinkoff.homework_2.data.model.ChannelListResponse
import kz.tinkoff.homework_2.data.model.PeopleListResponse

class FakeCommonFactory {

    fun getChannels(): ChannelListResponse {
        val list = buildList<ChannelListResponse.ChannelResponse?> {
            for (i in channels.indices) {
                add(
                    ChannelListResponse.ChannelResponse(
                        id = i,
                        name = channels[i],
                        testingMessageCount = i + 100,
                        brushMessageCount = i + 100
                    )
                )
            }
        }
        return ChannelListResponse(list)
    }

    fun findChannel(name: String): ChannelListResponse? {
        val list = getChannels().list?.filter { it?.name?.replace("#", "")?.contains(name) == true }
        return ChannelListResponse(list)
    }


    fun getPeople(): PeopleListResponse? {
        return PeopleListResponse(listOfPeople)
    }

    fun findPerson(name: String): PeopleListResponse? {
        val filter = getPeople()?.listResponse?.filter { it?.fullName?.contains(name) == true }
        return PeopleListResponse(filter)
    }

    companion object {
        private val channels = listOf(
            "#general",
            "#Development",
            "#Design",
            "#PR"
        )

        val listOfPeople = generatePeople()

        private fun generatePeople(): List<PeopleListResponse.PersonResponse> {
            val firstNames = listOf(
                "Emma",
                "Liam",
                "Olivia",
                "Noah",
                "Ava",
                "Elijah",
                "Charlotte",
                "William",
                "Sophia",
                "James",
                "Mia",
                "Benjamin",
                "Isabella",
                "Lucas",
                "Amelia",
                "Mason",
                "Harper",
                "Evelyn",
                "Ethan",
                "Abigail"
            )

            val lastNames = listOf(
                "Smith",
                "Johnson",
                "Williams",
                "Jones",
                "Brown",
                "Garcia",
                "Miller",
                "Davis",
                "Rodriguez",
                "Martinez",
                "Hernandez",
                "Lopez",
                "Gonzalez",
                "Perez",
                "Taylor",
                "Anderson",
                "Wilson",
                "Jackson",
                "Wright",
                "Moore"
            )

            val emails = listOf(
                "gmail.com", "yahoo.com", "hotmail.com", "outlook.com"
            )

            val personList = List(20) {
                val firstName = firstNames.random()
                val lastName = lastNames.random()
                val fullName = "$firstName $lastName"
                val email =
                    "$firstName.${lastName.lowercase(Locale.getDefault())}@${emails.random()}"
                val isOnline = listOf(true, false).random()

                PeopleListResponse.PersonResponse(
                    id = it,
                    fullName = fullName,
                    email = email,
                    isOnline = isOnline
                )
            }
            return personList
        }
    }
}