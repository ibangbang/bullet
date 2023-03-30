package com.github.lib.bullet.impl

import com.github.lib.bullet.core.IBullet

class DefaultBullet : IBullet {

    var duration = 1500L

    override fun getType(): Int {
        return 0
    }

    val name = "gift"
}