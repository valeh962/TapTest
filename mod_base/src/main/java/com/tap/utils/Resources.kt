package com.tap.utils

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Resources @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun string(id: Int) = context.getString(id)

    fun color(id: Int) = ContextCompat.getColor(context, id)

    fun dimen(id: Int) = context.resources.getDimension(id).toInt()

    fun drawable(id: Int) = ContextCompat.getDrawable(context, id)

    fun font(id: Int) = ResourcesCompat.getFont(context, id)
}