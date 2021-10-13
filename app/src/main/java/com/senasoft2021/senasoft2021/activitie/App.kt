package com.senasoft2021.senasoft2021.activitie

import android.app.Application

class App : Application() {
    companion object {
        lateinit var instance:Application
    }
    init {

        instance=this
    }
}