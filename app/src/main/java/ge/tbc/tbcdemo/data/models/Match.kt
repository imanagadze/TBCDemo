package ge.tbc.tbcdemo.data.models

data class Match(
    val matchTime: Float,
    val matchDate: Long,
    val stadiumAdress: String,
    val team1: Team,
    val team2: Team,
    val matchSummary: MatchSummary
)