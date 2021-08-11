package com.mongodb.ispfieldtechapp

import android.app.Application
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

class ISPFieldTechApplication : Application() {

    val techApp : App by lazy {
        App(AppConfiguration.Builder(BuildConfig.REALM_APP_ID).build())
    }

    override fun onCreate() {
        super.onCreate()
    }
}