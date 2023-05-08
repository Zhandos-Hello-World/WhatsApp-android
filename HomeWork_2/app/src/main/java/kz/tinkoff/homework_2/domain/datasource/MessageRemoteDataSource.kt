package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.MessageListResponse

interface MessageRemoteDataSource {

    suspend fun getAllMessage(stream: String, numBefore: Int = 1000, numAfter: Int = 1000): MessageListResponse

    suspend fun setMessageSend(request: HashMap<String, Any?>): BaseResponse

    suspend fun addReaction(messageId: Int, request: HashMap<String, String>): BaseResponse

    suspend fun deleteReaction(messageId: Int, request: HashMap<String, String>): BaseResponse
}
