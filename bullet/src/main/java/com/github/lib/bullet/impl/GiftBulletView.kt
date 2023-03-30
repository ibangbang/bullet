package com.github.lib.bullet.impl

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.github.lib.bullet.R
import com.github.lib.bullet.core.IBullet
import com.github.lib.bullet.core.IBulletView

class GiftBulletView(inputBullet: IBullet) : IBulletView {

    val bullet = inputBullet as GiftBullet

    override fun bindView(view: View) {
        view.findViewById<TextView>(R.id.contentTv).text = bullet.content
        view.findViewById<ImageView>(R.id.typeIv).setImageResource(R.drawable.bullet_ic_gift)
        view.findViewById<ImageView>(R.id.bgIv).setImageResource(R.drawable.bullet_bg_gift)
        view.findViewById<TextView>(R.id.actionTv).text = "去围观"
    }

    override fun getDisplayDuration(): Long {
        return bullet.duration
    }

    override fun onClick(activity: FragmentActivity) {
        super.onClick(activity)
        //点击
        Log.d("Gift", "nav to gift :$bullet")
    }

}