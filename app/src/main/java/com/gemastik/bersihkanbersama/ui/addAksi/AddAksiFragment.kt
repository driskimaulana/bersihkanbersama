package com.gemastik.bersihkanbersama.ui.addAksi

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.gemastik.bersihkanbersama.databinding.FragmentAddAksiBinding
import com.gemastik.bersihkanbersama.utils.MoneyTextWatcher
import com.gemastik.bersihkanbersama.utils.Utils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAksiFragment : Fragment() {
    private var _binding: FragmentAddAksiBinding? = null
    private val binding get() = _binding
    private var getFile: File? = null
    private val myCalendar = Calendar.getInstance()
    private val locale = Locale("id", "ID")

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it?.data?.data as Uri
            getFile = Utils.uriToFile(selectedImg, requireContext())
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

        val endDate =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                binding?.etEndDate?.let { updateLabel(it) }
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

            // End aksi DatePicker
            etEndDate.setOnClickListener {
                DatePickerDialog(
                    requireContext(),
                    endDate,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            // Currency formatting
            etDonation.addTextChangedListener(MoneyTextWatcher(etDonation))
            etDonation.setText("0")

            // To choose a picture from gallery
            etCoverPict.setOnClickListener {
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
                    val address = etAddress.text
                        .toString()
                        .trim()
                        .toRequestBody("text/plain".toMediaType())
                    // TODO IMAGE FILE HANDLER
//                    val requestImageFile = getFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
//                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                        "photo",
//                        getFile?.name,
//                        requestImageFile
//                    )
                    submitRequest()
                } else {
                    // TODO
                    Toast.makeText(requireContext(), "TODO", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // TODO
    private fun submitRequest() {

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