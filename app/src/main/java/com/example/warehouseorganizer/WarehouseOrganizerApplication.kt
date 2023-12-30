package com.example.warehouseorganizer

import android.app.Application
import com.example.warehouseorganizer.data.AppContainer
import com.example.warehouseorganizer.data.LoginContainer

class WarehouseOrganizerApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = LoginContainer()
    }
}