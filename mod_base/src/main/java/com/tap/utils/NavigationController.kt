package com.tap.utils

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.tap.mod_base.R

val navOptionsNoAnimation = NavOptions.Builder()
    .setEnterAnim(R.anim.no_animation)
    .setExitAnim(R.anim.no_animation)
    .setPopEnterAnim(R.anim.no_animation)
    .setPopExitAnim(R.anim.no_animation)
    .setLaunchSingleTop(false)

val navOptionsSlideRight = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .setLaunchSingleTop(false)


fun NavController.navigateToFragment(
    directions: NavDirections,
    bundle: Bundle? = null,
    navOptions: NavOptions.Builder? = navOptionsNoAnimation,
    isLaunchingTop: Boolean? = false
) {
    try {
        navOptions?.apply {
            setLaunchSingleTop(isLaunchingTop ?: false)
            navigate(directions.actionId, bundle, navOptions.build())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun NavController.navigateToFragment(
    directions: Int,
    bundle: Bundle? = null,
    navOptions: NavOptions.Builder? = navOptionsNoAnimation,
    isLaunchingTop: Boolean? = false
) {
    try {
        navOptions?.apply {
            setLaunchSingleTop(isLaunchingTop ?: false)
            navigate(directions, bundle, navOptions.build())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
