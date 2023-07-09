package com.gemastik.bersihkanbersama.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gemastik.bersihkanbersama.data.repositories.ActivityRepository
import com.gemastik.bersihkanbersama.data.repositories.ArticleRepository
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import com.gemastik.bersihkanbersama.data.repositories.DonationRepository
import com.gemastik.bersihkanbersama.di.Injection
import com.gemastik.bersihkanbersama.ui.viewmodels.DonationViewModel
import com.gemastik.bersihkanbersama.ui.login.LoginViewModel
import com.gemastik.bersihkanbersama.ui.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val activityRepository: ActivityRepository,
    private val donationRepository: DonationRepository,
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {
    //TODO
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(authRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(authRepository) as T
            modelClass.isAssignableFrom(DonationViewModel::class.java) -> DonationViewModel(donationRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

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