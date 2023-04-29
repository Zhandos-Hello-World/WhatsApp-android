package kz.tinkoff.homework_2.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresenceResponse(
    @SerialName("presence")
    val presence: Presence,
) {

    @Serializable
    data class Presence(
        @SerialName("aggregated")
        val aggregated: Aggregated,
    )

    @Serializable
    data class Aggregated(
        @SerialName("status")
        val status: String,
        @SerialName("timestamp")
        val timestamp: Long,
    )
}
