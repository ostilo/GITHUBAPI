package com.github.githubsearch

import android.app.Application
import com.github.githubsearch.data.di.githubModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GithubSearchApp)
            modules(
                githubModule
            )
        }
    }
}