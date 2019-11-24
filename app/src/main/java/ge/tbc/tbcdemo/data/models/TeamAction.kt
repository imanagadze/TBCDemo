package ge.tbc.tbcdemo.data.models

data class TeamAction(
    val teamType: MatchTeamType,
    val actionType: MatchActionType,
    val action: Action
)