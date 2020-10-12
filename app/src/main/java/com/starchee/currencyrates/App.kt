package com.starchee.currencyrates

import android.app.Application
import com.starchee.currencyrates.di.components.AppComponent
import com.starchee.currencyrates.di.components.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().withApplication(this).build()
    }
}