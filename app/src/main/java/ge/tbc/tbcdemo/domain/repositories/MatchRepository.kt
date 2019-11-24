package ge.tbc.tbcdemo.domain.repositories

import ge.tbc.tbcdemo.data.models.MatchResponse
import io.reactivex.Single

interface MatchRepository {

    fun getMatch(): Single<MatchResponse>
}