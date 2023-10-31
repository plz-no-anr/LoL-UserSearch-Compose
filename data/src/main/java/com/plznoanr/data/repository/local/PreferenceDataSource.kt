package com.plznoanr.data.repository.local

import android.content.Context
import android.content.SharedPreferences

class PreferenceDataSource (
    context: Context
) {
    companion object {
        const val LOL_APP = "LOL_APP"
        const val API_KEY = "API_KEY"
        const val INIT_KEY = "INIT_KEY"
    }
    private val prefs: SharedPreferences =
        context.getSharedPreferences(LOL_APP, Context.MODE_PRIVATE)

    var apiKey: String?
        get() = prefs.getString(API_KEY, "DEFAULT_API_KEY")
        set(value) {
            prefs.edit().putString(API_KEY, value).apply()
        }

    var isInit: Boolean
        get() = prefs.getBoolean(INIT_KEY, false)
        set(value) {
            prefs.edit().putBoolean(INIT_KEY, value).apply()
        }
}