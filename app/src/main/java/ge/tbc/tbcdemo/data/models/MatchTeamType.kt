package ge.tbc.tbcdemo.data.models

import com.google.gson.annotations.SerializedName

enum class MatchTeamType(val type: Int) {
    @SerializedName("1")
    TEAM1(1),
    @SerializedName("2")
    TEAM2(2)
}