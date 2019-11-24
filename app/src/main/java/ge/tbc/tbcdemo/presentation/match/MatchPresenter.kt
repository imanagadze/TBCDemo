package ge.tbc.tbcdemo.presentation.match

import android.util.Log
import ge.tbc.tbcdemo.app.scopes.PerActivity
import ge.tbc.tbcdemo.data.models.Summary
import ge.tbc.tbcdemo.domain.interactors.GetMatchInteractor
import ge.tbc.tbcdemo.presentation.base.BasePresenter
import ge.tbc.tbcdemo.presentation.helper.getDateFromMills
import javax.inject.Inject

@PerActivity
class MatchPresenter @Inject constructor(
    private val getMatchInteractor: GetMatchInteractor
) : BasePresenter<MatchView>() {

    override fun onFirstAttach() {
        addDisposable(getMatchInteractor.execute().subscribe({ response ->
            view?.hideProgress()
            val match = response.match
            view?.populateMatchDate(getDateFromMills(match.matchDate, "dd MMMM yyyy"))
            view?.populateStadium(match.stadiumAdress)
            view?.populateTeam1(match.team1.teamName, match.team1.teamImage)
            view?.populateTeam2(match.team2.teamName, match.team2.teamImage)
            view?.populateScore(
                "${match.team1.score} : ${match.team2.score}",
                "${match.matchTime.toInt()}"
            )
            view?.populatePossession(match.team1.ballPosition, match.team2.ballPosition)
            view?.populateActions(getRVCollection(match.matchSummary.summaries.sortedBy { it.actionTime }))

        }, {
            view?.hideProgress()
            Log.e("TBC Footbal", it.message + " " + it.toString())
        }))
    }

    private fun getRVCollection(summaries: List<Summary>): List<RVActionModel> {
        val result = mutableListOf<RVActionModel>()
        for (summ in summaries) {
            val actionTime = summ.actionTime
            var i = 0
            val count =
                if (summ.team1Action?.size ?: 0 > summ.team2Action?.size ?: 0) summ.team1Action?.size
                    ?: 0
                else summ.team2Action?.size ?: 0
            while (i < count) {
                val action1 =
                    if (summ.team1Action?.size ?: 0 > i) summ.team1Action?.get(i) else null
                val action2 =
                    if (summ.team2Action?.size ?: 0 > i) summ.team2Action?.get(i) else null
                result.add(RVActionModel(actionTime, action1, action2))
                i++
            }
        }
        return result
    }
}