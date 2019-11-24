package ge.tbc.tbcdemo.data.models

import com.google.gson.annotations.SerializedName

enum class MatchActionType(val type: Int) {
    @SerializedName("1")
    GOAL(1),
    @SerializedName("2")
    YELLOW_CARD(2),
    @SerializedName("3")
    RED_CARD(3),
    @SerializedName("4")
    SUBSTITUTION(4)
}