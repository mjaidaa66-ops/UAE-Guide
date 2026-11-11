package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "uae_pay_preferences")

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val FAVORITE_SERVICES = stringSetPreferencesKey("favorite_services")
        val RECENT_SERVICES_CSV = stringPreferencesKey("recent_services_csv")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }

    val favoriteServiceIds: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FAVORITE_SERVICES] ?: emptySet()
    }

    suspend fun toggleFavorite(serviceId: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[PreferencesKeys.FAVORITE_SERVICES]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(serviceId)) {
                current.remove(serviceId)
            } else {
                current.add(serviceId)
            }
            preferences[PreferencesKeys.FAVORITE_SERVICES] = current
        }
    }

    val recentServiceIds: Flow<List<String>> = context.dataStore.data.map { preferences ->
        val csv = preferences[PreferencesKeys.RECENT_SERVICES_CSV] ?: ""
        if (csv.isBlank()) emptyList() else csv.split(",").filter { it.isNotBlank() }
    }

    suspend fun recordRecentService(serviceId: String) {
        context.dataStore.edit { preferences ->
            val current = (preferences[PreferencesKeys.RECENT_SERVICES_CSV] ?: "")
                .split(",")
                .filter { it.isNotBlank() }
                .toMutableList()

            current.remove(serviceId)
            current.add(0, serviceId)
            val updated = current.take(5)
            preferences[PreferencesKeys.RECENT_SERVICES_CSV] = updated.joinToString(",")
        }
    }
}
