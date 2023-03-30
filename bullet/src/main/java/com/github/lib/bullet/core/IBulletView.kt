package com.github.lib.bullet.core

import android.view.View
import androidx.fragment.app.FragmentActivity

interface IBulletView {

    fun bindView(view: View)

    fun getDisplayDuration(): Long = 1000

    fun getEnterDuration(): Long = 500

    fun getExitDuration(): Long = 200

    fun onClick(activity: FragmentActivity) {}

}
