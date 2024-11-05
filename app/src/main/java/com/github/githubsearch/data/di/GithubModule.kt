package com.github.githubsearch.data.di

import com.github.githubsearch.BuildConfig
import com.github.githubsearch.data.api.RepoApiService
import com.github.githubsearch.data.repository.RepoRepositoryImpl
import com.github.githubsearch.domain.repository.RepoRepository
import com.github.githubsearch.domain.usecase.GetAllRepositoryUseCase
import com.github.githubsearch.ui.screens.mainapp.MainAppViewState
import com.github.githubsearch.ui.screens.mainapp.SharedViewModel
import com.github.githubsearch.ui.screens.repositories.RepositoriesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val githubModule = module {

    viewModel { SharedViewModel( get()) }

    viewModel { RepositoriesViewModel( get()) }

    single { MainAppViewState() }

    single { GetAllRepositoryUseCase(get()) }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    //.header("Authorization", "Bearer ${BuildConfig.GITHUB_API_KEY}")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_BASE_URL)
            .client(get()) // Inject OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(RepoApiService::class.java)
    }

    single<RepoRepository> {
        RepoRepositoryImpl(get())
    }
}