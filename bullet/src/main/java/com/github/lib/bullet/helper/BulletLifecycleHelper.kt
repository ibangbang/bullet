package com.github.lib.bullet.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

object BulletLifecycleHelper {

    private val activityRefList = mutableListOf<WeakReference<Activity>>()
    lateinit var appContext: Application

    fun init(application: Application) {
        appContext = application
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                createActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                pageStatusListeners.forEach {
                    it.onResume(activity)
                }
            }

            override fun onActivityPaused(activity: Activity) {
                pageStatusListeners.forEach {
                    it.onPause(activity)
                }
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                destroyActivity(activity)
            }
        })
    }

    private fun createActivity(activity: Activity) {
        activityRefList.add(0, WeakReference(activity))
    }

    private fun destroyActivity(activity: Activity) {
        activityRefList.removeIf {
            it.get() == activity
        }
    }

    fun getCurrentActivity(): Activity? {
        return activityRefList.firstOrNull()?.get()
    }

    /** 页面状态更改回调 */
    private val pageStatusListeners = CopyOnWriteArrayList<OnPageStatusChangedListener>()

    fun addOnPageStatusChangedListener(listener: OnPageStatusChangedListener) {
        pageStatusListeners.add(listener)
    }

    fun removeOnPageStatusChangedListener(listener: OnPageStatusChangedListener) {
        pageStatusListeners.remove(listener)
    }

    /** 页面状态更改回调 */
    interface OnPageStatusChangedListener {
        fun onResume(activity: Activity) {}
        fun onPause(activity: Activity) {}
    }

}