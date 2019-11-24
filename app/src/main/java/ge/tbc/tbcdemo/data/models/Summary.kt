package ge.tbc.tbcdemo.data.models

data class Summary(
    val actionTime: Int,
    val team1Action: List<TeamAction>?,
    val team2Action: List<TeamAction>?
)