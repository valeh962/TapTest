package com.tap.utils

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ThreadManager @Inject constructor() {

    private var executors: ExecutorService = Executors.newCachedThreadPool()
    private var handler: Handler = Handler(Looper.getMainLooper())
    private val job = Job()

    fun postUI(runnable: Runnable, time: Long? = 0L) = handler.postDelayed(runnable, time ?: 0L)

    fun postBack(runnable: Runnable): Future<*> = executors.submit(runnable)

    fun scopeIO() = CoroutineScope(job + Dispatchers.IO)

    fun scopeUI() = CoroutineScope(Dispatchers.Main)

    fun killThread() = executors.shutdown()
}