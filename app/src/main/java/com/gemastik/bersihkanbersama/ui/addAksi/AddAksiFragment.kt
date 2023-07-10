package com.gemastik.bersihkanbersama.ui.addAksi

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gemastik.bersihkanbersama.data.models.AccountModel
import com.gemastik.bersihkanbersama.databinding.FragmentAddAksiBinding
import com.gemastik.bersihkanbersama.ui.DonationPayment.DonationPaymentActivity
import com.gemastik.bersihkanbersama.ui.detailaksi.DetailAksiActivity
import com.gemastik.bersihkanbersama.ui.viewmodels.AksiViewModel
import com.gemastik.bersihkanbersama.utils.MoneyTextWatcher
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.Utils
import com.gemastik.bersihkanbersama.utils.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAksiFragment : Fragment() {
    private var _binding: FragmentAddAksiBinding? = null
    private val binding get() = _binding
    private var getFile: File? = null
    private val myCalendar = Calendar.getInstance()
    private val locale = Locale("id", "ID")

    private lateinit var startDate: Date

    private val viewModel: AksiViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var user: AccountModel

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it?.data?.data as Uri
            getFile = Utils.uriToFile(selectedImg, requireContext())
            binding?.etCoverPict?.setText(getFile!!.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAksiBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            lifecycleScope.launch {
                viewModel.getAccount().collect{
                    user = it
                }
            }
            setupView()
        }
    }

    private fun setupView() {
        activity?.actionBar?.hide()

        // Setup DatePickerDialog
        val startDate =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                binding?.etStartDate?.let { updateLabel(it) }
            }

        binding?.apply {
            // Start aksi DatePicker
            etStartDate.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    startDate,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            // To choose a picture from gallery
            etCoverPict.setOnClickListener {
                Log.d("driskidebug", "Choose Imaeg")
//                val intent = Intent()
//                intent.action = Intent.ACTION_GET_CONTENT
//                intent.type = "image/*"
//                val chooser = Intent.createChooser(intent, "Choose a Picture")
//                launcherIntentGallery.launch(chooser)
            }

            btnUpload.setOnClickListener {
                Log.d("driskidebug", "Hallo")
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }

            // TODO
            // To submit a request
            btnSubmit.setOnClickListener {
                if (getFile != null) {
                    val title = etAksiName.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val description = etDescAksi.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    // TODO LOCATION
                    val city = etLocation.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val fullAddress = etAddress.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val participationRewards = etAddress.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val firstRewards = etFirst.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val secondRewards = etSecond.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val thirdRewards = etThird.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    val eventDate = myCalendar.time.toString().trim().toRequestBody("text/plain".toMediaType())
                    // TODO IMAGE FILE HANDLER
                    val requestImageFile = getFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part? = requestImageFile?.let { it1 ->
                        MultipartBody.Part.createFormData(
                            "coverImage",
                            getFile?.name,
                            it1
                        )
                    }

                    if (imageMultipart != null) {
                        submitRequest(title, description, eventDate, participationRewards, firstRewards, secondRewards, thirdRewards, imageMultipart, city, fullAddress)
                    }
                } else {
                    // TODO
                    Toast.makeText(requireContext(), "Silakan lengkapi form.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // TODO
    private fun submitRequest(
        title: RequestBody,
        description: RequestBody,
        eventDate: RequestBody,
        participationReward: RequestBody,
        firstRewards: RequestBody,
        secondRewards: RequestBody,
        thirdRewards: RequestBody,
        coverImage: MultipartBody.Part,
        city: RequestBody,
        fullAddress: RequestBody
    ) {
        Log.d("driskidebug", user.token)
        viewModel.createNewActivity(
            user.token.toString(),
            title, description, eventDate, participationReward, firstRewards, secondRewards, thirdRewards, coverImage, city, fullAddress
        ).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        loadingState(true)
                    }

                    is Result.Success -> {
                        loadingState(false)
                        val intent = Intent(requireContext(), DetailAksiActivity::class.java)
                        intent.putExtra(DetailAksiActivity.ACTIVITY_EXTRA, it.data.activityId)
                        startActivity(intent)
                    }

                    is Result.Error -> {
                        loadingState(false)
                        Log.d("driskidebug", it.error.toString())
                        Toast.makeText(requireContext(), "Gagal membuat aksi. Coba lagi nanti.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        Log.d("driskidebug", "Error")
                    }
                }
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding?.apply {
                btnSubmit.visibility = View.INVISIBLE
                submitLoading.visibility = View.VISIBLE
            }
        } else {
            binding?.apply {
                btnSubmit.visibility = View.VISIBLE
                submitLoading.visibility = View.GONE
            }
        }
    }

    // Update date label
    private fun updateLabel(editText: EditText) {
        val myFormat = "dd MMMM yyyy"
        val dateFormat = SimpleDateFormat(myFormat, locale)
        editText.setText(dateFormat.format(myCalendar.time))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}