package com.github.lib.bullet.impl

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.github.lib.bullet.R
import com.github.lib.bullet.core.IBullet
import com.github.lib.bullet.core.IBulletView

class RedPacketBulletView(inputBullet: IBullet) : IBulletView {

    val bullet = inputBullet as RedPacketBullet

    override fun bindView(view: View) {
        view.findViewById<TextView>(R.id.contentTv).text = bullet.content
        view.findViewById<ImageView>(R.id.typeIv).setImageResource(R.drawable.bullet_ic_red_packet)
        view.findViewById<ImageView>(R.id.bgIv).setImageResource(R.drawable.bullet_bg_red_packet)
    }

    override fun getDisplayDuration(): Long {
        return bullet.duration
    }

    override fun onClick(activity: FragmentActivity) {
        super.onClick(activity)
    }

}