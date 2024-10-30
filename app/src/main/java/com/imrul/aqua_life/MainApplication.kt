package com.imrul.aqua_life

import android.app.Application
import androidx.room.Room
import com.imrul.aqua_life.dataset.Database
import java.io.File

class MainApplication : Application() {
    companion object {
        lateinit var database : Database
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            Database.NAME
        ).build()
    }
}

