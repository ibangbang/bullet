package com.github.app.bullet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.lib.bullet.core.BulletLogic
import com.github.lib.bullet.impl.GiftBullet
import com.github.lib.bullet.impl.RedPacketBullet

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBullet()
    }

    private fun initBullet() {

    }

    fun newBullet(view: View) {
        BulletLogic.insertBullet(GiftBullet())
    }

    fun newRed(view: View) {
        BulletLogic.insertBullet(RedPacketBullet())
    }

    fun clear(view: View) {
        BulletLogic.clear()
    }

    fun next(view: View) {
        startActivity(Intent(this, NextActivity::class.java))
    }

    override fun onDestroy() {
        BulletLogic.onDestroy()
        super.onDestroy()
    }

}