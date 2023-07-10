package com.gemastik.bersihkanbersama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.bersihkanbersama.databinding.FragmentHomeBinding
import com.gemastik.bersihkanbersama.ui.adapters.ActivityListAdapter
import com.gemastik.bersihkanbersama.ui.adapters.ArticleListAdapter
import com.gemastik.bersihkanbersama.utils.Result
import com.gemastik.bersihkanbersama.utils.ViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val ongoingActivityAdapter = ActivityListAdapter()
    private val closeActivityAdapter = ActivityListAdapter()
    private val articleAdapter = ArticleListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setupHeader()
            setupOngoingActivity()
            setupCloseActivity()
            setupArticle()
        }
    }

    private fun setupHeader() {
        loadingState(true)
        viewModel.getAccount().observe(viewLifecycleOwner) { account ->
            if (account.role == "User") {
                viewModel.getUser(account.token)
                    .observe(viewLifecycleOwner) {
                        when (it) {
                            is Result.Loading -> {
                                loadingState(true)
                            }

                            is Result.Success -> {
                                loadingState(false)
                                binding?.apply {
                                    tvUsername.text = account.name
                                    tvPoint.text = it.data.points.totalPoints.toString()
                                    tvActivity.text = it.data.activities?.size.toString()
                                }
                            }

                            is Result.Error -> {
                                loadingState(false)
                                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            } else {
                binding?.apply {
                    tvUsername.text = account.name
                    tvPoint.text = "0"
                    tvActivity.text = "0"
                }
            }
        }
    }

    private fun setupOngoingActivity() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvOngoingActivity?.layoutManager = layoutManager
        binding?.rvOngoingActivity?.setHasFixedSize(true)
        binding?.rvOngoingActivity?.adapter = ongoingActivityAdapter

        viewModel.getAllActivity().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    val data = it.data.activities.filter { activity ->
                        activity.status == "Started"
                    }
                    ongoingActivityAdapter.submitList(data)
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupCloseActivity() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvClosestActivity?.layoutManager = layoutManager
        binding?.rvClosestActivity?.setHasFixedSize(true)
        binding?.rvClosestActivity?.adapter = closeActivityAdapter

        viewModel.getAllActivity().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    val data = it.data.activities.filter { activity ->
                        activity.status != "Finished"
                    }
                    closeActivityAdapter.submitList(data)
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupArticle() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvArticle?.layoutManager = layoutManager
        binding?.rvArticle?.setHasFixedSize(true)
        binding?.rvArticle?.adapter = articleAdapter

        viewModel.getAllArticle().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    loadingState(true)
                }

                is Result.Success -> {
                    loadingState(false)
                    articleAdapter.submitList(it.data.articles)
                }

                is Result.Error -> {
                    loadingState(false)
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
                tvUsername.visibility = View.INVISIBLE
                ivPoint.visibility = View.INVISIBLE
                tvPoint.visibility = View.INVISIBLE
                tvTotalPoint.visibility = View.INVISIBLE
                ivActivity.visibility = View.INVISIBLE
                tvActivity.visibility = View.INVISIBLE
                tvTotalActivity.visibility = View.INVISIBLE
                tvOngoingActivity.visibility = View.GONE
                tvMore1.visibility = View.GONE
                rvOngoingActivity.visibility = View.GONE
                view1.visibility = View.GONE
                tvClosestActivity.visibility = View.GONE
                tvMore2.visibility = View.GONE
                rvClosestActivity.visibility = View.GONE
                view2.visibility = View.GONE
                tvNewArticle.visibility = View.GONE
                tvMore3.visibility = View.GONE
                rvArticle.visibility = View.GONE
            } else {
                pbLoading.visibility = View.GONE
                tvUsername.visibility = View.VISIBLE
                ivPoint.visibility = View.VISIBLE
                tvPoint.visibility = View.VISIBLE
                tvTotalPoint.visibility = View.VISIBLE
                ivActivity.visibility = View.VISIBLE
                tvActivity.visibility = View.VISIBLE
                tvTotalActivity.visibility = View.VISIBLE
                tvOngoingActivity.visibility = View.VISIBLE
                tvMore1.visibility = View.VISIBLE
                rvOngoingActivity.visibility = View.VISIBLE
                view1.visibility = View.VISIBLE
                tvClosestActivity.visibility = View.VISIBLE
                tvMore2.visibility = View.VISIBLE
                rvClosestActivity.visibility = View.VISIBLE
                view2.visibility = View.VISIBLE
                tvNewArticle.visibility = View.VISIBLE
                tvMore3.visibility = View.VISIBLE
                rvArticle.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}