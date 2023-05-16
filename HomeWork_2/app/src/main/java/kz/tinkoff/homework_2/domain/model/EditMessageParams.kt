package kz.tinkoff.homework_2.domain.model

data class EditMessageParams(
    val topic: String,
    val propagateMode: PropagateMode = PropagateMode.CHANGE_ONE,
    val sendNotificationToOldThread: Boolean = false,
    val sendNotificationToNewThread: Boolean = true,
    val content: String,
    val streamId: Int,
) {

    enum class PropagateMode(val type: String) {
        CHANGE_LATER("change_later"),
        CHANGE_ONE("change_one"),
        CHANGE_ALL("change_all")
    }
}
