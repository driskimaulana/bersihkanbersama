package com.gemastik.bersihkanbersama.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gemastik.bersihkanbersama.databinding.FragmentProfileBinding
import com.gemastik.bersihkanbersama.ui.SplashActivity
import com.gemastik.bersihkanbersama.ui.viewmodels.ProfileViewModel
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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

        binding?.btnLogout?.setOnClickListener {
            Log.d("driskidebug", "setupView: 1")
            viewModel.logout()
            val intent = Intent(activity, SplashActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
