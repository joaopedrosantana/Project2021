package com.example.app_test

import android.app.Application
import androidx.test.runner.AndroidJUnitRunner
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.app_test.data.remote.service.GitHubService
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestRunner : AndroidJUnitRunner() {

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        loadKoinModules(module {
            single(override = true) { retrofitCreateTest<GitHubService>(get()) }
        })
    }

    inline fun <reified T> retrofitCreateTest(okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8081/")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(T::class.java)
    }

}