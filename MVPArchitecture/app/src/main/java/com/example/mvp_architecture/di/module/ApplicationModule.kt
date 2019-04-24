package com.example.mvp_architecture.di.module

import android.app.Application
import com.example.mvp_architecture.BaseApp
import com.example.mvp_architecture.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApp){

    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application{
        return baseApp
    }
}