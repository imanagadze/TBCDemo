package ge.tbc.tbcdemo.presentation.match

import ge.tbc.tbcdemo.presentation.base.BaseView

interface MatchView : BaseView {

    fun populateMatchDate(date: String)

    fun populateStadium(stadiumName: String)

    fun populateTeam1(name: String, icon: String)

    fun populateTeam2(name: String, icon: String)

    fun populateScore(score: String, time: String)

    fun populatePossession(ball1: Int, ball2: Int)

    fun hideProgress()

    fun populateActions(actions: List<RVActionModel>)
}