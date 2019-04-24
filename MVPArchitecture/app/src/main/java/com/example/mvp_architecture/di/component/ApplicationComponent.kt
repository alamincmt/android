package com.example.mvp_architecture.di.component

import com.example.mvp_architecture.BaseApp
import com.example.mvp_architecture.di.module.ApplicationModule
import dagger.Component


@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: BaseApp)
}