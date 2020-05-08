package com.bettr.api.injection.component

import com.bettr.api.injection.module.NetworkModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, NetworkModule::class]
)
interface AppComponent {
//    fun inject(baseActivity: BaseActivity)
}