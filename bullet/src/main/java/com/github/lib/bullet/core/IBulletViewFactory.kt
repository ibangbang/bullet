package com.github.lib.bullet.core

import android.content.Context
import com.github.lib.bullet.R
import com.github.lib.bullet.impl.DefaultBulletView
import com.github.lib.bullet.impl.GiftBulletView

interface IBulletViewFactory {

    fun createView(context: Context, bullet: IBullet): Int
    fun createBulletView(context: Context, bullet: IBullet): IBulletView

    class Default : IBulletViewFactory {
        override fun createView(context: Context, bullet: IBullet): Int {
            val resId = when (bullet.getType()) {
                1 -> R.layout.bullet_layout_default
                else -> R.layout.bullet_layout_default
            }
            return resId
        }

        override fun createBulletView(
            context: Context,
            bullet: IBullet
        ): IBulletView {
            return when (bullet.getType()) {
                1 -> GiftBulletView(bullet)
                else -> DefaultBulletView(bullet)
            }
        }

        companion object {
            val INSTANCE = Default()
        }
    }
}