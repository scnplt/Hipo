package dev.sertan.hipoproject.data.repository

import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.source.HipoService
import dev.sertan.hipoproject.data.source.LocalDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HipoRepository @Inject constructor(
    private val service: HipoService,
    private val localSource: LocalDataSource
) {
    val responseState = localSource.serviceResponse.map { response ->
        if (response.isFailure) service.getMembers().body()?.let { localSource.saveResponse(it) }
        response
    }

    fun updateMembers(members: List<Member>) = localSource.updateMembers(members)
}
