package com.gemastik.bersihkanbersama.ui.forum.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.bersihkanbersama.databinding.FragmentForumHomeBinding

class ForumHomeFragment : Fragment() {
    private var _binding: FragmentForumHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForumHomeBinding.inflate(inflater, container, false)
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

        // Setup recycler view
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFeed?.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            // TODO Adapter
        }
    }

    // Managing loading state view
    private fun loadingState(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                rvFeed.visibility = View.GONE
                fabAdd.visibility = View.GONE
                tvLoading.visibility = View.VISIBLE
                pbLoading.visibility = View.VISIBLE
            } else {
                rvFeed.visibility = View.VISIBLE
                fabAdd.visibility = View.VISIBLE
                tvLoading.visibility = View.GONE
                pbLoading.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}