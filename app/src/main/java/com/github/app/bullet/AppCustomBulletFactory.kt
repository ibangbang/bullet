package com.github.app.bullet

import android.content.Context
import com.github.lib.bullet.core.IBullet
import com.github.lib.bullet.core.IBulletView
import com.github.lib.bullet.core.IBulletViewFactory
import com.github.lib.bullet.impl.DefaultBulletView
import com.github.lib.bullet.impl.GiftBulletView
import com.github.lib.bullet.impl.RedPacketBulletView

class AppCustomBulletFactory:IBulletViewFactory {
    override fun createView(context: Context, bullet: IBullet): Int {
        return when(bullet.getType()){
            1-> com.github.lib.bullet.R.layout.bullet_layout_default
            else -> com.github.lib.bullet.R.layout.bullet_layout_default
        }
    }

    override fun createBulletView(context: Context, bullet: IBullet): IBulletView {
        return when (bullet.getType()) {
            1 -> GiftBulletView(bullet)
            2 -> RedPacketBulletView(bullet)
            else -> DefaultBulletView(bullet)
        }
    }
}