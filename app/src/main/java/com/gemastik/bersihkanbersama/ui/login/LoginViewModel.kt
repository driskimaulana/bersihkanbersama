package com.gemastik.bersihkanbersama.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun userSignIn(email: String, password: String) = authRepository.userSignIn(email, password)

    fun organizationSignIn(email: String, password: String) = authRepository.organizationSignIn(email, password)

    fun saveAccount(account: AccountModel){
        viewModelScope.launch {
            authRepository.saveAccount(account)
        }
    }
}