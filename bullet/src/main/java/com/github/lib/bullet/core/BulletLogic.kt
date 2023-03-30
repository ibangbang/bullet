package com.github.lib.bullet.core

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.lib.bullet.helper.BulletLifecycleHelper
import com.github.lib.bullet.helper.BulletWindowHelper
import kotlinx.coroutines.*

/**
 * BulletLogic for bullet show
 */
object BulletLogic {

    const val TAG = "BulletLogic"

    private var bulletViewFactory: IBulletViewFactory = IBulletViewFactory.Default.INSTANCE

    private var consumeJob: Job? = null

    private var isBackend = false

    fun init(appContext: Application, factory: IBulletViewFactory? = null) {
        factory?.apply {
            bulletViewFactory = this
        }
        BulletLifecycleHelper.init(appContext)
        initListeners()
        initJob()
    }

    private fun initJob() {
        resumeConsumeJob()
    }

    private fun resumeConsumeJob() {
        if (consumeJob?.isActive == true) {
            return
        }
        consumeJob?.cancel()
        var isFirst = true
        consumeJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                if (!isFirst) {
                    delay(1000)
                }else{
                    isFirst = false
                }
                handleConsumeBullet()
                if (!BulletState.hasBullet()) {
                    cancel()
                }
            }
        }
    }

    private fun stopConsumeJob() {
        consumeJob?.cancel()
    }

    private fun handleConsumeBullet() {
        if (BulletState.isShow || isBackend) {
            return
        }
        val bullet = BulletState.dequeue() ?: return
        val currentActivity =
            BulletLifecycleHelper.getCurrentActivity() as? FragmentActivity ?: return
        prepareView(currentActivity, bullet)
    }

    private fun prepareView(context: FragmentActivity, bullet: IBullet) {
        BulletState.isShow = true
        val resId = bulletViewFactory.createView(context, bullet)
        val bulletView = bulletViewFactory.createBulletView(context, bullet)
        CoroutineScope(Dispatchers.Main).launch {
            BulletWindowHelper.addToCurrentWindow(context, resId, bulletView)
        }
    }

    private val appLifecycleObserver: LifecycleEventObserver =
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    handleLifeToBackend(false)
                }
                Lifecycle.Event.ON_STOP -> {
                    handleLifeToBackend(true)
                }
                else -> {}
            }
        }

    private val pageStatusListener: BulletLifecycleHelper.OnPageStatusChangedListener =
        object : BulletLifecycleHelper.OnPageStatusChangedListener {
            override fun onPause(activity: Activity) {
                if (activity is FragmentActivity) {
                    BulletWindowHelper.removeFromActivity(activity)
                }
            }
        }

    private fun initListeners() {
        BulletLifecycleHelper.addOnPageStatusChangedListener(pageStatusListener)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }

    private fun handleLifeToBackend(isBack: Boolean) {
        Log.d(TAG, "handleLifeToBackend $isBack")
        isBackend = isBack
        if (isBack) {
            stopConsumeJob()
        } else {
            resumeConsumeJob()
        }
    }

    fun insertBullet(bullet: IBullet) {
        BulletState.enqueue(bullet)
        resumeConsumeJob()
    }

    fun clear(){
        BulletState.clear()
        stopConsumeJob()
    }

    fun onDestroy() {
        consumeJob?.cancel()
        BulletState.clear()
        BulletState.isShow = false
        BulletLifecycleHelper.removeOnPageStatusChangedListener(pageStatusListener)
        ProcessLifecycleOwner.get().lifecycle.removeObserver(appLifecycleObserver)
    }

}


