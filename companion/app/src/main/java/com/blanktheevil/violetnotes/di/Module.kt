package com.blanktheevil.violetnotes.di

import com.blanktheevil.violetnotes.data.api.NotesApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val appModule = module {
    single {
        OkHttpClient.Builder().build()
    }

    single {
        Moshi.Builder()
            .build()
    }

    single<NotesApi> {
        Retrofit.Builder()
            .baseUrl(NotesApi.URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
            .create()
    }
}