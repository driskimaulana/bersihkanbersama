package com.gemastik.bersihkanbersama.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gemastik.bersihkanbersama.data.local.AccountPreference
import com.gemastik.bersihkanbersama.data.remote.retrofit.ApiConfig
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.ArticleRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import com.gemastik.bersihkanbersama.data.repositories.DonationRepository

/***
 * Object for manual dependency injection
 */
object Injection {
    fun provideUserRepository(dataStore: DataStore<Preferences>): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val pref = AccountPreference.getInstance(dataStore)
        return AuthRepository.getInstance(apiService, pref)
    }

    fun provideActivityRepository(): ActivityRepository {
        val apiService = ApiConfig.getApiService()
        return ActivityRepository.getInstance(apiService)
    }

    fun provideDonationRepository(): DonationRepository {
        val apiService = ApiConfig.getApiService()
        return DonationRepository.getInstance(apiService)
    }

    fun provideArticleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }
}