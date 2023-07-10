package com.gemastik.bersihkanbersama.ui.forum.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.databinding.FragmentForumHomeBinding
import com.gemastik.bersihkanbersama.ui.adapters.forum.ForumAdapter
import com.gemastik.bersihkanbersama.ui.adapters.forum.ForumItem

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

        val posts = listOf<ForumItem>(
            ForumItem("D'Riski Maulana", "Bandung", "Hari ini", R.drawable.driski, "Tumpukan sampah di hulu sungai Cimanuk yang menyebabkan aliran air tersendat", R.drawable.cimanuk, 100, 45),
            ForumItem("Novaldi Sandi Ago", "Pangandaran", "Hari ini", R.drawable.nopal, "Sampah berserakan di sekitar pantai pangandaran setelah libur lebaran beberapa minggu yang lalu.", R.drawable.maxresdefault, 80, 20),
            ForumItem("Gifari Octaverin", "Bandung", "Kemarin", R.drawable.user, "Kondisi jalanan Bandung terkini. Terlihat sampah berserakan, butuh bantuan untuk membersihkannya kembali.", R.drawable.jalan, 70, 55),
        )

        // Setup recycler view
        val layoutManager = LinearLayoutManager(requireContext())
        val forumAdapter = ForumAdapter(requireContext(), posts)
        binding?.rvFeed?.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            // TODO Adapter
            adapter = forumAdapter
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