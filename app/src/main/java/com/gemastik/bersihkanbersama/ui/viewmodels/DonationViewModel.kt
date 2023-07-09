package com.gemastik.bersihkanbersama.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gemastik.bersihkanbersama.data.remote.request.DonationRequest
import com.gemastik.bersihkanbersama.data.repositories.DonationRepository
import retrofit2.Response

class DonationViewModel(private val donationRepository: DonationRepository) : ViewModel() {

    fun createNewDonation( token: String,
                           id: String,
                           requestBody: DonationRequest
    ) = donationRepository.createNewDonation(token, id, requestBody)

    fun getPaymentDetails(
        id: String
    ) = donationRepository.getPaymentDetails(id)

}