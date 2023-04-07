package kz.tinkoff.homework_2.data.model

data class PeopleListResponse(
    val listResponse: List<PersonResponse>
) {

    data class PersonResponse(
        val id: Int,
        val fullName: String,
        val email: String,
        val isOnline: Boolean,
    )
}
