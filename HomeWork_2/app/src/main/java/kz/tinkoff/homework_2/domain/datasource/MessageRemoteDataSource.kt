package kz.tinkoff.homework_2.domain.datasource

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.MessageListResponse

interface MessageRemoteDataSource {

    suspend fun getAllMessage(): MessageListResponse

    suspend fun setMessageSend(request: HashMap<String, Any?>): BaseResponse

    suspend fun addReaction(messageId: Int, request: HashMap<String, String>): BaseResponse

    suspend fun deleteReaction(messageId: Int, request: HashMap<String, String>): BaseResponse
}