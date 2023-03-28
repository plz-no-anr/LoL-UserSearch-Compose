package com.plznoanr.data.repository.local

import android.content.Context
import android.content.SharedPreferences
import com.plznoanr.data.utils.DEFAULT_API_KEY

class PreferenceDataSource (
    context: Context
) {
    companion object {
        private const val LOL_APP = "LOL_APP"
        const val API_KEY = "API_KEY"
        const val LOCAL_DATA_INIT = "LOCAL_DATA_INIT"
    }
    private val prefs: SharedPreferences =
        context.getSharedPreferences(LOL_APP, Context.MODE_PRIVATE)

    var apiKey: String?
        get() = prefs.getString(API_KEY, DEFAULT_API_KEY)
        set(value) {
            prefs.edit().putString(API_KEY, value).apply()
        }

    var isInit: Boolean
        get() = prefs.getBoolean(LOCAL_DATA_INIT, false)
        set(value) {
            prefs.edit().putBoolean(LOCAL_DATA_INIT, value).apply()
        }
}