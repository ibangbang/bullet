package com.github.lib.bullet.helper

import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

object BulletActivityHelper {

    fun getContentView(activity: FragmentActivity): ViewGroup? {
        val rootView = activity.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
        return rootView?.getChildAt(0) as? ViewGroup
    }

    fun isFinish(activity: FragmentActivity): Boolean {
        return activity.isFinishing || activity.isDestroyed
    }

}