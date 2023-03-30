package com.github.app.bullet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.lib.bullet.core.BulletLogic
import com.github.lib.bullet.impl.GiftBullet

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
    }

    fun batchBullet(view: View) {
        repeat(10) {
            BulletLogic.insertBullet(GiftBullet())
        }
        Toast.makeText(this, "10 bullet created", Toast.LENGTH_SHORT).show()
    }
}