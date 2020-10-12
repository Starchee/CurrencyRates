package com.starchee.currencyrates.di.components

import android.app.Application
import com.starchee.currencyrates.activities.RatesActivity
import com.starchee.currencyrates.di.modules.OkHttpClientModule
import com.starchee.currencyrates.di.modules.RatesDatabaseModule
import com.starchee.currencyrates.di.modules.RetrofitServiceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        OkHttpClientModule::class,
        RetrofitServiceModule::class,
        RatesDatabaseModule::class])
interface AppComponent {

    fun inject(ratesActivity: RatesActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun withApplication(application: Application) : Builder
        fun build(): AppComponent
    }
}