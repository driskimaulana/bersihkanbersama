package com.gemastik.bersihkanbersama.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(
    @field:SerializedName("leaderboard")
    val leaderboard: List<TeamResultResponse>
)
