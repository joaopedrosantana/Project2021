package com.example.app_test.di

import com.example.app_test.data.remote.ApiClient.retrofitCreate
import com.example.app_test.data.remote.GitHubDataSource
import com.example.app_test.data.remote.GitHubDataSourceImpl
import com.example.app_test.data.repository.GitHubRepository
import com.example.app_test.data.repository.GitHubRepositoryImpl
import com.example.app_test.util.isNetworkConnected
import com.example.app_test.data.remote.service.GitHubService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModules {
    val CACHE_SIZE = (10 * 1024 * 1024).toLong()
    val CACHE_TIME = (60 * 60 * 24).toLong()

    val serviceModules = module {
        single { retrofitCreate<GitHubService>(get()) }

        single<Interceptor> {
            val isConnected = androidContext().isNetworkConnected()
            object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = if (isConnected)
                        request.newBuilder().header("Cache-Control", "public, max-age=5").build()
                    else
                        request.newBuilder().header(
                            "Cache-Control",
                            "public, only-if-cached, max-stale=$CACHE_TIME"
                        ).build()
                    return chain.proceed(request)
                }
            }
        }

        single {
            val myCache = Cache(androidContext().cacheDir, CACHE_SIZE)
            OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(get<Interceptor>())
                .build()
        }
    }

    val dataModules = module {
        single<GitHubRepository> { GitHubRepositoryImpl(get()) }

        single<GitHubDataSource> {
            GitHubDataSourceImpl(get())
        }
    }
}