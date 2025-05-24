package com.example.mapita.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object IdiomaUtils {

    fun setAppLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    fun getCurrentLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPref.getString("selected_language", "es") ?: "es"
    }
}