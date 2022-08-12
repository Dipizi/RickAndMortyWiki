package com.dipizi007.rickandmorty.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.dipizi007.rickandmorty.R

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes text: Int, name: String): String {
        return context.getString(text, name)
    }

    fun getString(@StringRes text: Int): String {
        return context.getString(text)
    }

    fun getColor(@ColorRes color: Int): Int {
        return context.getColor(color)
    }
}