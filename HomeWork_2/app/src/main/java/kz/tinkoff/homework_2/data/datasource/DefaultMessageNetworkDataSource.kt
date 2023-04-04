package kz.tinkoff.homework_2.data.datasource

import kz.tinkoff.homework_2.data.model.BaseResponse
import kz.tinkoff.homework_2.data.model.MessageListResponse
import kz.tinkoff.homework_2.data.network.ApiService
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource

class DefaultMessageNetworkDataSource(private val apiService: ApiService): MessageRemoteDataSource {

    override suspend fun getAllMessage(): MessageListResponse {
        return apiService.getAllMessage()
    }

    override suspend fun setMessageSend(request: HashMap<String, Any?>): BaseResponse {
        return apiService.sendMessageStream(request)
    }

    override suspend fun addReaction(messageId: Int, request: HashMap<String, String>): BaseResponse {
        return apiService.addReaction(messageId, request)
    }

    override suspend fun deleteReaction(
        messageId: Int,
        request: HashMap<String, String>,
    ): BaseResponse {
        return apiService.deleteReaction(messageId, request)
    }

}