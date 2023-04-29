package kz.tinkoff.homework_2.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.data.network.MessageApiService
import kz.tinkoff.homework_2.data.network.PeopleApiService
import kz.tinkoff.homework_2.data.network.StreamApiService
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit

const val AUTHORIZATION = "Authorization"

val networkModule = module {

    single<OkHttpClient> { provideClient(get()) }

    single<Converter.Factory> { provideSerializationFactory() }

    single<Retrofit> { provideRetrofit(converter = get(), httpClient = get()) }

    single<MessageApiService> { get<Retrofit>().create(MessageApiService::class.java) }

    single<StreamApiService> { get<Retrofit>().create(StreamApiService::class.java) }

    single<PeopleApiService> { get<Retrofit>().create(PeopleApiService::class.java) }

}

fun provideRetrofit(converter: Converter.Factory, httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(converter)
        .client(httpClient).build()
}

private fun provideClient(context: Context): OkHttpClient {
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

fun provideSerializationFactory(contentType: MediaType = "application/json".toMediaType()): Converter.Factory {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    return json.asConverterFactory(contentType)
}
