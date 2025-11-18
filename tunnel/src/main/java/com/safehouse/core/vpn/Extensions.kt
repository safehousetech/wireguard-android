package com.safehouse.core.vpn

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

fun Context.restartApplication() {
    val homeIntent =
        Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_HOME)
            .setPackage(packageName)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val pi =
        PendingIntent.getActivity(
            this,
            42, // The answer to everything
            homeIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or
                    PendingIntent.FLAG_ONE_SHOT or
                    PendingIntent.FLAG_IMMUTABLE
        )
    getSystemService<AlarmManager>()?.let {
        AlarmManagerCompat.setExact(
            it,
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + 500,
            pi
        )
        Handler().postDelayed({ android.os.Process.killProcess(android.os.Process.myPid()) }, 500L)
    }
}

val Any.applicationScope: CoroutineScope
    get() = GlobalScope

