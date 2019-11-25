package ge.tbc.tbcdemo.presentation.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ge.tbc.tbcdemo.R
import ge.tbc.tbcdemo.presentation.base.BaseActivity
import ge.tbc.tbcdemo.presentation.helper.getPxFromDp
import ge.tbc.tbcdemo.presentation.helper.getScreenWidth
import ge.tbc.tbcdemo.presentation.helper.loadIcon
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt


class MatchActivity : BaseActivity<MatchPresenter>(), MatchView {

    private val adapter = ActionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvActions.layoutManager = LinearLayoutManager(this)
        rvActions.adapter = adapter

        presenter.attach(this)
    }

    override fun populateMatchDate(date: String) {
        txtMatchDate.text = date
    }

    override fun populateStadium(stadiumName: String) {
        txtStadiumName.text = stadiumName
    }

    override fun populateTeam1(name: String, icon: String) {
        txtTeam1Name.text = name
        ivTeam1Icon.loadIcon(icon)
    }

    override fun populateTeam2(name: String, icon: String) {
        txtTeam2Name.text = name
        ivTeam2Icon.loadIcon(icon)
    }

    override fun populateScore(score: String, time: String) {
        txtScore.text = score
        txtTime.text = time
    }

    @SuppressLint("SetTextI18n")
    override fun populatePossession(ball1: Int, ball2: Int) {
        txtBallPossession1.text = "$ball1 %"
        txtBallPossession2.text = "$ball2 %"

        viewBall1.layoutParams.width =
            (((getScreenWidth() - getPxFromDp(80F)) * ball1) / 100).roundToInt()

    }

    override fun hideProgress() {
        progress.visibility = View.GONE
        clRoot.visibility = View.VISIBLE
    }

    override fun populateActions(actions: List<RVActionModel>) {
        adapter.setItems(actions)
    }
}