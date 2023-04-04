package kz.tinkoff.homework_2.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kz.tinkoff.homework_2.BuildConfig
import kz.tinkoff.homework_2.data.network.ApiService
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val AUTHORIZATION = "Authorization"

val networkModule = module {
    single<GsonConverterFactory> { provideGson() }

    single<OkHttpClient> { provideClient(get()) }

    single<Retrofit> { provideRetrofit(gson = get(), httpClient = get()) }

    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }


}

fun provideRetrofit(gson: GsonConverterFactory, httpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(gson)
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

fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()
