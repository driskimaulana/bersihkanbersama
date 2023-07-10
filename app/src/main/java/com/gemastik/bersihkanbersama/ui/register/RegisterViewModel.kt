package com.gemastik.bersihkanbersama.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.data.remote.request.OrganizationSignUpRequest
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun userSignUp(name: String, phone: String, email: String, password: String) = authRepository.userSignUp(name, phone, email, password)

    fun organizationSignUp(orgSignupRequest: OrganizationSignUpRequest) = authRepository.organizationSignUp(orgSignupRequest)

    fun saveAccount(account: AccountModel){
        viewModelScope.launch {
            authRepository.saveAccount(account)
        }
    }
}