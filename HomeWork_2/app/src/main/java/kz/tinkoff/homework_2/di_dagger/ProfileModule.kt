package kz.tinkoff.homework_2.di_dagger

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.data.datasource.DefaultPeopleNetworkDataSource
import kz.tinkoff.homework_2.data.mappers.PersonMapper
import kz.tinkoff.homework_2.data.mappers.PresenceMapper
import kz.tinkoff.homework_2.data.mappers.ProfileMapper
import kz.tinkoff.homework_2.data.network.PeopleApiService
import kz.tinkoff.homework_2.data.repository.RepoPeopleImpl
import kz.tinkoff.homework_2.di.AUTHORIZATION
import kz.tinkoff.homework_2.domain.datasource.PeopleRemoteDataSource
import kz.tinkoff.homework_2.domain.repository.PeopleRepository
import kz.tinkoff.homework_2.presentation.mapper.MessageDelegateItemMapper
import kz.tinkoff.homework_2.presentation.mapper.MessageDvoMapper
import kz.tinkoff.homework_2.presentation.mapper.ProfileDvoMapper
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileActor
import kz.tinkoff.homework_2.presentation.profile.elm.ProfileStoreFactory
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class ProfileModule(private val context: Context) {

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
    fun provideMessageApiService(retrofit: Retrofit): PeopleApiService {
        return retrofit.create(PeopleApiService::class.java)
    }

    @Provides
    fun provideMessageRemoteDataSource(apiService: PeopleApiService): PeopleRemoteDataSource {
        return DefaultPeopleNetworkDataSource(apiService)
    }

    @Provides
    fun providePersonMapper(): PersonMapper {
        return PersonMapper()
    }

    @Provides
    fun provideProfileMapper(): ProfileMapper {
        return ProfileMapper()
    }

    @Provides
    fun providePresenceMapper(): PresenceMapper {
        return PresenceMapper()
    }

    @Provides
    fun provideDvoMapper(): ProfileDvoMapper {
        return ProfileDvoMapper()
    }


    @Provides
    fun provideProfileRepository(
        dataSource: PeopleRemoteDataSource,
        personMapper: PersonMapper,
        profileMapper: ProfileMapper,
        presenceMapper: PresenceMapper,
    ): PeopleRepository {
        return RepoPeopleImpl(
            dataSource,
            personMapper,
            profileMapper,
            presenceMapper
        )
    }

    @Provides
    fun provideProfileActor(
        repository: PeopleRepository,
        dvoMapper: ProfileDvoMapper,
    ): ProfileActor {
        return ProfileActor(
            repository,
            dvoMapper
        )
    }

    @Provides
    fun provideProfileFactory(actor: ProfileActor): ProfileStoreFactory {
        return ProfileStoreFactory(
            actor
        )
    }

}
