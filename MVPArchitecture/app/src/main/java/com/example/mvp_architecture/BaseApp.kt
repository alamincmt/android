package com.example.mvp_architecture

import android.app.Application
import com.example.mvp_architecture.di.component.ApplicationComponent
import dagger.android.DaggerApplication

class BaseApp : Application() {

    /*lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    fun setup() {
        component = DaggerApplication
    }*/
}