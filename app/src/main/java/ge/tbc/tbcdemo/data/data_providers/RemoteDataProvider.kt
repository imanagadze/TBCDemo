package ge.tbc.tbcdemo.data.data_providers

import ge.tbc.tbcdemo.data.models.MatchResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RemoteDataProvider {

    @GET("$VERSION/5b9264193300006b00205fb9")
    fun getMatch(): Single<MatchResponse>
}