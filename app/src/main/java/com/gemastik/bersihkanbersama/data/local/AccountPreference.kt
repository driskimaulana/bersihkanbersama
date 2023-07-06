package com.gemastik.bersihkanbersama.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/***
 * To store account information
 */
class AccountPreference private constructor(private val dataStore: DataStore<Preferences>) {
    // TODO

    companion object {
        // TODO KEYS

        // Make this class singleton
        @Volatile
        private var instance: AccountPreference? = null
        fun getInstance(
            dataStore: DataStore<Preferences>
        ): AccountPreference =
            instance ?: synchronized(this) {
                instance?: AccountPreference(dataStore)
            }.also {
                instance = it
            }
    }
}