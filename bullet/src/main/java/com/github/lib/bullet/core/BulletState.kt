package com.github.lib.bullet.core

import java.util.*

object BulletState {

    var isShow = false

    private val bulletList = Collections.synchronizedList(mutableListOf<IBullet>())

    fun enqueue(bullet: IBullet) = bulletList.add(bullet)

    fun dequeue(): IBullet? = bulletList.removeFirstOrNull()

    fun hasBullet(): Boolean = bulletList.isNotEmpty()

    fun size(): Int = bulletList.size

    fun clear() = bulletList.clear()

}