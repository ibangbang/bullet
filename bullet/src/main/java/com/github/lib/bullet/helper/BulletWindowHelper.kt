package com.github.lib.bullet.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.github.lib.bullet.core.BulletState
import com.github.lib.bullet.core.IBulletView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max

object BulletWindowHelper {

    const val TAG = "BulletWindowHelper"

    private val displayWidth by lazy { BulletLifecycleHelper.appContext.applicationContext.resources.displayMetrics.widthPixels.toFloat() }

    suspend fun addToCurrentWindow(
        activity: FragmentActivity,
        resId: Int,
        bulletView: IBulletView
    ) {
        withContext(Dispatchers.Main) {
            val contentView = BulletActivityHelper.getContentView(activity) ?: return@withContext
            val view = LayoutInflater.from(activity).inflate(resId, contentView, false)
            view.tag = "tag_bullet_${activity}"
            contentView.addView(view)
            bulletView.bindView(view)
            val enterDuration = max(bulletView.getEnterDuration(), 0)
            val exitDuration = max(bulletView.getExitDuration(), 0)
            val displayDuration = max(bulletView.getDisplayDuration(), 0)
            view.setOnClickListener {
                bulletView.onClick(activity)
            }
            showEnterAnim(view, enterDuration) {
                if (BulletActivityHelper.isFinish(activity)) {
                    removeByLifecycle(contentView, view)
                    return@showEnterAnim
                }
                view.postDelayed(
                    {
                        if (BulletActivityHelper.isFinish(activity)) {
                            removeByLifecycle(contentView, view)
                            return@postDelayed
                        }
                        showExitAnim(view, exitDuration) {
                            removeByLifecycle(contentView, view)
                        }
                    }, displayDuration
                )
            }

        }
    }

    private fun removeByLifecycle(contentView: ViewGroup, view: View) {
        BulletState.isShow = false
        contentView.removeView(view)
    }

    private fun showEnterAnim(view: View, enterDuration: Long, animEnd: () -> Unit) {
        view.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(view, "translationX", displayWidth, 0f).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animEnd()
                }
            })
            duration = enterDuration
            start()
        }
    }

    private fun showExitAnim(view: View, exitDuration: Long, animEnd: () -> Unit) {
        ObjectAnimator.ofFloat(view, "translationX", 0f, -displayWidth).apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animEnd()
                }
            })
            duration = exitDuration
            start()
        }
    }

    fun removeFromActivity(activity: FragmentActivity) {
        val contentView = BulletActivityHelper.getContentView(activity) ?: return
        contentView.findViewWithTag<View>("tag_bullet_${activity}")?.let { contentView.removeView(it) }
    }

}