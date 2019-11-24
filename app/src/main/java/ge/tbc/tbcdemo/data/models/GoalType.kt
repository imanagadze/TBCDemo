package ge.tbc.tbcdemo.data.models

import com.google.gson.annotations.SerializedName

enum class GoalType(val type: Int) {
    @SerializedName("1")
    GOAL(1),
    @SerializedName("1")
    OWN_GOAL(2)
}