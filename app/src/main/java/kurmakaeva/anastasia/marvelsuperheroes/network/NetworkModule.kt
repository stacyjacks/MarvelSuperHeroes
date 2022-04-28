package kurmakaeva.anastasia.marvelsuperheroes.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request()
                val newRequestHttpUrl = newRequest.url
                val timestamp = (Calendar.getInstance(
                    TimeZone.getTimeZone(TIMEZONE)).timeInMillis / 1000L).toString()
                val url = newRequestHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, pubkey)
                    .addQueryParameter(TIMESTAMP, timestamp)
                    .addQueryParameter(HASH, "$timestamp$privkey$pubkey".md5)
                    .build()
                chain.proceed(newRequest.newBuilder().url(url).build())
            }.build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)

}
