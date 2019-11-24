package ge.tbc.tbcdemo.domain.interactors

import ge.tbc.tbcdemo.data.models.MatchResponse
import ge.tbc.tbcdemo.domain.repositories.MatchRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetMatchInteractor @Inject constructor(private val matchRepository: MatchRepository) {

    fun execute(): Single<MatchResponse> {
        return matchRepository.getMatch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}