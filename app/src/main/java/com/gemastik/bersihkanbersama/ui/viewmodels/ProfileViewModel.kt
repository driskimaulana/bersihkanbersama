package com.gemastik.bersihkanbersama.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemastik.bersihkanbersama.data.repositories.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun logout(){
        viewModelScope.launch {
            authRepository.deleteAccount()
        }
    }
}