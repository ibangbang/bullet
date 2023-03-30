package com.github.lib.bullet.impl

import android.view.View
import android.widget.TextView
import com.github.lib.bullet.R
import com.github.lib.bullet.core.IBullet
import com.github.lib.bullet.core.IBulletView

class DefaultBulletView(inputBullet: IBullet) : IBulletView {

    val bullet = inputBullet as DefaultBullet

    override fun bindView(view: View) {
        view.findViewById<TextView>(R.id.contentTv).text = bullet.name
    }

    override fun getDisplayDuration(): Long {
        return bullet.duration
    }

}