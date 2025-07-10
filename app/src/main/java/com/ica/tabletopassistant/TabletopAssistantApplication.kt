package com.ica.tabletopassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TabletopAssistantApplication : Application() {
    // You can override onCreate() here if you need to do other
    // application-wide initializations, but for Hilt's basic setup,
    // the @HiltAndroidApp annotation is the key.
    override fun onCreate() {
        super.onCreate()
        // Other app initializations if needed
    }
}