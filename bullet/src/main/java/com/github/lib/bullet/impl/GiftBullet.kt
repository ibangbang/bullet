package com.github.lib.bullet.impl

import com.github.lib.bullet.core.IBullet

class GiftBullet : IBullet {

    var duration = 2000L

    var senderName = "Jones"
    var receiverName = "Lily"
    var giftId = 1001
    var giftName = "摩天轮"
    var giftCount = "30"
    var giftUnit = "座"
    var content = "Jones 送给 Lily 30座摩天轮"

    override fun getType(): Int {
        return 1
    }

    val name = "gift"
}