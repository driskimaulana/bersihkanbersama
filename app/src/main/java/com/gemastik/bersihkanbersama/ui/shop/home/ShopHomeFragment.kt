package com.gemastik.bersihkanbersama.ui.shop.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.FragmentShopHomeBinding
import com.gemastik.bersihkanbersama.ui.adapters.shop.ShopAdapter
import com.gemastik.bersihkanbersama.ui.adapters.shop.ShopItem

class ShopHomeFragment : Fragment() {
    private var _binding: FragmentShopHomeBinding? = null
    private val binding get() = _binding

    private val shopData = listOf<ShopItem>(
        ShopItem(R.drawable.tokopedia, "Voucher Tokopedia 50K", 5000),
        ShopItem(R.drawable.tokopedia, "Voucher Tokopedia 20K", 3000),
        ShopItem(R.drawable.tokopedia, "Voucher Tokopedia 10K", 2000),
        ShopItem(R.drawable.kaos_save_earth, "Kaos Save Earth", 15000),
        ShopItem(R.drawable.lavida_mug, "Mug Lavida", 7000),
        ShopItem(R.drawable.tumbler, "Tumbler", 10000),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pullToRefresh: SwipeRefreshLayout = binding!!.refresh
        pullToRefresh.setOnRefreshListener { // your code
            pullToRefresh.isRefreshing = false
        }

        if (activity != null) {
            setupView()
        }
    }

    private fun setupView() {
        activity?.actionBar?.hide()

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val shopAdapter = ShopAdapter(requireContext(), shopData)
        binding?.rvItem?.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            // TODO Adapter
            this.adapter = shopAdapter
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