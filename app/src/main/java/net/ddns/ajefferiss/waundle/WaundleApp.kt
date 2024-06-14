package net.ddns.ajefferiss.waundle

import android.app.Application

class WaundleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}