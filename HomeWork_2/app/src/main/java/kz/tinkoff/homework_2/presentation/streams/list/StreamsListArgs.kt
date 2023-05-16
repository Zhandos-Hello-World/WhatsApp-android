package kz.tinkoff.homework_2.presentation.streams.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StreamsListArgs(
    val requestType: StreamRequest = StreamRequest.ALL_STREAMS,
    val selectTopicWithResultListener: Boolean = false
) : Parcelable {

    @Parcelize
    enum class StreamRequest : Parcelable {
        ALL_STREAMS, SUBSCRIBED
    }
}
