package com.example.mapita.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.util.Locale

class IdiomaViewModel(application: Application) : AndroidViewModel(application) {

    var currentLanguage by mutableStateOf(fetchCurrentLanguage()) // Changed here
        private set

    private fun fetchCurrentLanguage(): String {
        val sharedPref = getApplication<Application>().getSharedPreferences(
            "app_preferences", Context.MODE_PRIVATE
        )
        return sharedPref.getString("selected_language", "es") ?: "es"
    }

    fun changeLanguage(languageCode: String) {
        currentLanguage = languageCode
        saveLanguagePreference(languageCode)
        setLocale(languageCode)
    }

    private fun saveLanguagePreference(languageCode: String) {
        val sharedPref = getApplication<Application>().getSharedPreferences(
            "app_preferences", Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString("selected_language", languageCode)
            apply()
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val context = getApplication<Application>()
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration) // You might want to update the activity/app context with this new configuration
    }
}