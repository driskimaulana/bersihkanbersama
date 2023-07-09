package com.gemastik.bersihkanbersama.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gemastik.bersihkanbersama.data.models.AccountModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/***
 * To store account information
 */
class AccountPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getAccount(): Flow<AccountModel> =
        dataStore.data.map {
            AccountModel(
                id = it[ID_KEY] ?: "",
                token = it[TOKEN_KEY] ?: "",
                role = it[ROLE_KEY] ?: ""
            )
        }

    suspend fun saveAccount(account: AccountModel) {
        dataStore.edit {
            it[ID_KEY] = account.id
            it[TOKEN_KEY] = account.token
            it[ROLE_KEY] = account.role
        }
    }

    suspend fun deleteAccount() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val ID_KEY = stringPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ROLE_KEY = stringPreferencesKey("role")

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