package com.gemastik.bersihkanbersama.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.ArticleRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import com.gemastik.bersihkanbersama.data.repositories.DonationRepository
import com.gemastik.bersihkanbersama.di.Injection

class ViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository,
    private val donationRepository: DonationRepository,
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {
    //TODO
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//
//        }
//    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "account")

        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context.dataStore),
                    Injection.provideActivityRepository(),
                    Injection.provideDonationRepository(),
                    Injection.provideArticleRepository()
                )
            }.also {
                instance = it
            }
    }
}