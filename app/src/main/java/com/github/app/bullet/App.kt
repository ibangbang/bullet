package com.github.app.bullet

import android.app.Application
import com.github.lib.bullet.core.BulletLogic

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initBullet()
    }

    private fun initBullet() {
        BulletLogic.init(this, AppCustomBulletFactory())
    }

}