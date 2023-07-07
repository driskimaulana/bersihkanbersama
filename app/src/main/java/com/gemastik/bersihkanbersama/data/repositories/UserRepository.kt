package com.gemastik.bersihkanbersama.data.repositories

import com.gemastik.bersihkanbersama.data.local.AccountPreference
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiService

/***
 * repository for account information
 */
class UserRepository private constructor(
    private val apiService: ApiService,
    private val preference: AccountPreference
) {

    // Make this class singleton
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            preference: AccountPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, preference)
            }.also {
                instance = it
            }
    }
}