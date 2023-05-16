package kz.tinkoff.homework_2.data.mappers

import kz.tinkoff.core.Mapper
import kz.tinkoff.homework_2.domain.model.EditMessageParams

class EditDtoMessageMapper : Mapper<EditMessageParams, HashMap<String, String>> {

    override fun map(from: EditMessageParams): HashMap<String, String> {
        val filters = hashMapOf<String, String>()
        filters[TOPIC] = from.topic
        filters[PROPAGATE_MODE] = from.propagateMode.type
        filters[SEND_NOTIFICATION_TO_OLD_THREAD] = from.sendNotificationToOldThread.toString()
        filters[SEND_NOTIFICATION_TO_NEW_THREAD] = from.sendNotificationToNewThread.toString()
        filters[CONTENT] = from.content
        filters[STREAM_ID] = from.streamId.toString()
        return filters
    }

    fun map(from: EditMessageParams, changeContent: Boolean = false): HashMap<String, String> {
        return map(from).also {
            if (!changeContent) {
                it.remove(CONTENT)
            } else {
                it.remove(STREAM_ID)
                it.remove(TOPIC)
            }
        }
    }

    companion object {
        const val TOPIC = "topic"
        const val PROPAGATE_MODE = "propagate_mode"
        const val SEND_NOTIFICATION_TO_OLD_THREAD = "send_notification_to_old_thread"
        const val SEND_NOTIFICATION_TO_NEW_THREAD = "send_notification_to_new_thread"
        const val CONTENT = "content"
        const val STREAM_ID = "stream_id"
    }
}
