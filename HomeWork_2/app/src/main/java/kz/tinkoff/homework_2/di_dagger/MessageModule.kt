package kz.tinkoff.homework_2.di_dagger

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.github.terrakok.cicerone.Router
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.data.datasource.DefaultMessageNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.MessageDtoMapper
import kz.tinkoff.homework_2.data.mappers.MessageMapper
import kz.tinkoff.homework_2.data.mappers.ReactionDtoMapper
import kz.tinkoff.homework_2.data.network.MessageApiService
import kz.tinkoff.homework_2.data.repository.RepoMessageImpl
import kz.tinkoff.homework_2.di.AUTHORIZATION
import kz.tinkoff.homework_2.domain.datasource.MessageRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.MessageRepository
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ReactionViewItemMapper
import kz.tinkoff.homework_2.presentation.message.elm.MessageActor
import kz.tinkoff.homework_2.presentation.message.elm.MessageStoreFactory
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class MessageModule(private val context: Context, private val router: Router) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideClient(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(ChuckerInterceptor.Builder(context).build())
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val original = chain.request()

            val credentials = Credentials.basic(BuildConfig.EMAIL, BuildConfig.API_KEY)

            val requestBuilder = original.newBuilder()
            requestBuilder.header(AUTHORIZATION, credentials).method(original.method, original.body)
                .build()

            chain.proceed(requestBuilder.build())
        })
        return httpClient.build()
    }

    @Provides
    fun provideSerializationFactory(): Converter.Factory {
        val contentType: MediaType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }


    @Provides
    fun provideRetrofit(converter: Converter.Factory, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(converter)
            .client(httpClient).build()
    }

    @Provides
    fun provideMessageApiService(retrofit: Retrofit): MessageApiService {
        return retrofit.create(MessageApiService::class.java)
    }

    @Provides
    fun provideMessageRemoteDataSource(apiService: MessageApiService): MessageRemoteDataSource {
        return DefaultMessageNetworkDataSource(apiService)
    }

    @Provides
    fun provideMessageRepository(
        dataSource: MessageRemoteDataSource,
        messageMapper: MessageMapper,
        messageDtoMapper: MessageDtoMapper,
        reactionDtoMapper: ReactionDtoMapper,
    ): MessageRepository {
        return RepoMessageImpl(
            dataSource,
            messageMapper,
            messageDtoMapper,
            reactionDtoMapper
        )
    }

    @Provides
    fun provideMessageActor(
        repository: MessageRepository,
        delegateItemMapper: MessageDelegateItemMapper,
    ): MessageActor {
        return MessageActor(
            repository,
            delegateItemMapper,
            router = router,
        )
    }

    @Provides
    fun provideMessageFactory(actor: MessageActor): MessageStoreFactory {
        return MessageStoreFactory(
            actor
        )
    }

    @Provides
    fun provideMessageDelegateItemMapper(
        dvoMapper: MessageDvoMapper,
    ): MessageDelegateItemMapper {
        return MessageDelegateItemMapper(
            dvoMapper
        )
    }

    @Provides
    fun provideMessageMapper(): MessageMapper {
        return MessageMapper()
    }

    @Provides
    fun provideMessageDvoMapper(reactionMapper: ReactionViewItemMapper): MessageDvoMapper {
        return MessageDvoMapper(reactionMapper)
    }

    @Provides
    fun provideMessageDtoMapper(): MessageDtoMapper {
        return MessageDtoMapper()
    }

    @Provides
    fun provideReactionMapper(): ReactionViewItemMapper {
        return ReactionViewItemMapper()
    }

    @Provides
    fun provideReactionDtoMapper(): ReactionDtoMapper {
        return ReactionDtoMapper()
    }
}
