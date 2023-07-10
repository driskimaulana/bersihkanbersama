package com.gemastik.bersihkanbersama.ui.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gemastik.bersihkanbersama.R
import com.gemastik.bersihkanbersama.data.models.LeaderboardUserModel
import com.gemastik.bersihkanbersama.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var leaderboardAdapter: LeaderboardAdapter

    private val userList = listOf(
        LeaderboardUserModel("Monkey D. Luffy!", 25000),
        LeaderboardUserModel("Kazuto Kirigaya", 24360),
        LeaderboardUserModel("Lord Kazuma", 21400),
        LeaderboardUserModel("Asuna Yuuki", 20000),
        LeaderboardUserModel("Shino Asada", 18000),
        LeaderboardUserModel("Natsu Dragneel", 17000),
        LeaderboardUserModel("Erza Scarlet", 16000),
        LeaderboardUserModel("Ichigo Kurosaki", 15000),
        LeaderboardUserModel("Gon Freecss", 14000),
        LeaderboardUserModel("Killua Zoldyck", 13000)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaderboardAdapter = LeaderboardAdapter(userList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leaderboardAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
