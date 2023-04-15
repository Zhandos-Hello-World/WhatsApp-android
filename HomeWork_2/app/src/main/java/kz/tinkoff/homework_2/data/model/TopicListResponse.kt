package kz.tinkoff.homework_2.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// Зачем Parcelable?
@Parcelize
data class TopicListResponse(
    val topics: List<TopicResponse>
): Parcelable {

    @Parcelize
    data class TopicResponse(
        @SerializedName("max_id")
        val id: Int,
        val name: String,
    ): Parcelable
}
