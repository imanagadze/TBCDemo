package ge.tbc.tbcdemo.data.repositories

import ge.tbc.tbcdemo.data.data_providers.RemoteDataProvider
import ge.tbc.tbcdemo.domain.repositories.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val remoteDataProvider: RemoteDataProvider
) : MatchRepository {

    override fun getMatch() = remoteDataProvider.getMatch()
}