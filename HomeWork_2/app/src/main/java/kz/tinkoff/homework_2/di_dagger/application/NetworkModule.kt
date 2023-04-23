package kz.tinkoff.homework_2.di_dagger.application

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import kz.tinkoff.homework_2.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    @Singleton
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
    @Singleton
    fun provideSerializationFactory(): Converter.Factory {
        val contentType: MediaType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideRetrofit(converter: Converter.Factory, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(converter)
            .client(httpClient).build()
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}
