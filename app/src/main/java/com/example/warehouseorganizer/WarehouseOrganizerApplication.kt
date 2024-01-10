package com.example.warehouseorganizer

import android.app.Application
import com.example.warehouseorganizer.data.WarehouseOrganizerContainer

class WarehouseOrganizerApplication : Application() {
    lateinit var appContainer: WarehouseOrganizerContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = WarehouseOrganizerContainer()
    }
}