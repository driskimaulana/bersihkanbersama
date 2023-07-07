package com.gemastik.bersihkanbersama.ui.shop.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gemastik.bersihkanbersama.databinding.FragmentShopHomeBinding

class ShopHomeFragment : Fragment() {
    private var _binding: FragmentShopHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopHomeBinding.inflate(inflater, container, false)
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

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.rvItem?.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            // TODO Adapter
        }
    }

    // Managing loading state view
    private fun loadingState(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                tvLoading.visibility = View.VISIBLE
                pbLoading.visibility = View.VISIBLE
                rvItem.visibility = View.GONE
            } else {
                tvLoading.visibility = View.GONE
                pbLoading.visibility = View.GONE
                rvItem.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}