package com.satyaai.app.di

import android.content.Context
import androidx.room.Room
import com.satyaai.app.BuildConfig
import com.satyaai.app.data.local.ChatDao
import com.satyaai.app.data.local.SatyaDatabase
import com.satyaai.app.data.remote.OpenAiApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SatyaDatabase =
        Room.databaseBuilder(context, SatyaDatabase::class.java, "satya_ai.db").build()

    @Provides
    fun provideChatDao(db: SatyaDatabase): ChatDao = db.chatDao()

    @Provides
    @Singleton
    fun provideApi(): OpenAiApi {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder().addInterceptor(logger).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPENAI_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(OpenAiApi::class.java)
    }
}
