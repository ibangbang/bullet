package com.github.lib.bullet.impl

import com.github.lib.bullet.core.IBullet

class RedPacketBullet : IBullet {

    var duration = 2000L
    var content = "Jones 发了一个红包，快来看看"

    override fun getType(): Int {
        return 2
    }

    val name = "redpacket"
}