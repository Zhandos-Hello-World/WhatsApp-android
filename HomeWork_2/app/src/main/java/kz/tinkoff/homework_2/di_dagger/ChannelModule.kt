package kz.tinkoff.homework_2.di_dagger

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.github.terrakok.cicerone.Router
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.data.datasource.DefaultStreamNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.StreamMapper
import kz.tinkoff.homework_2.data.mappers.TopicMapper
import kz.tinkoff.homework_2.data.network.StreamApiService
import kz.tinkoff.homework_2.data.repository.RepoStreamImpl
import kz.tinkoff.homework_2.di.AUTHORIZATION
import kz.tinkoff.homework_2.domain.datasource.ChannelRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.ChannelRepository
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelActor
import kz.tinkoff.homework_2.presentation.channels.elm.ChannelStoreFactory
import kz.tinkoff.homework_2.presentation.mapper.StreamDvoMapper
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class ChannelModule(private val context: Context, private val router: Router) {


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
    fun provideMessageApiService(retrofit: Retrofit): StreamApiService {
        return retrofit.create(StreamApiService::class.java)
    }

    @Provides
    fun provideDvoMapper(): StreamDvoMapper {
        return StreamDvoMapper()
    }

    @Provides
    fun provideStreamMapper(): StreamMapper {
        return StreamMapper()
    }

    @Provides
    fun provideTopicMapper(): TopicMapper {
        return TopicMapper()
    }

    @Provides
    fun provideChannelRemoteDataSource(apiService: StreamApiService): ChannelRemoteDataSource {
        return DefaultStreamNetworkDataSource(apiService)
    }


    @Provides
    fun provideChannelRepository(
        dataSource: ChannelRemoteDataSource,
        streamMapper: StreamMapper,
        topicMapper: TopicMapper,
    ): ChannelRepository {
        return RepoStreamImpl(dataSource, streamMapper, topicMapper)
    }


    @Provides
    fun provideChannelActor(
        repository: ChannelRepository,
        dvoMapper: StreamDvoMapper,
    ): ChannelActor {
        return ChannelActor(
            repository,
            dvoMapper,
            CoroutineScope(Dispatchers.IO)
        )
    }


    @Provides
    fun provideChannelFactory(actor: ChannelActor, context: Context): ChannelStoreFactory {
        return ChannelStoreFactory(
            actor,
            router = router
        )
    }


}
