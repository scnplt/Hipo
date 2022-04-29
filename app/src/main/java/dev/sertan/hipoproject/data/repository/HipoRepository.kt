package dev.sertan.hipoproject.data.repository

import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.source.HipoService
import dev.sertan.hipoproject.data.source.LocalDataSource
import dev.sertan.hipoproject.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HipoRepository @Inject constructor(
    private val service: HipoService,
    private val localSource: LocalDataSource
) {
    val responseState = flow {
        emit(State.loading())
        val response = localSource.serviceResponse.value
        if (response.isFailure) {
            service.getMembers().body()
                ?.let { localSource.saveResponse(it) }
                ?: emit(State.failure(response.exception))
        }
        emitAll(localSource.serviceResponse)
    }.catch { e -> emit(State.failure(e)) }.flowOn(Dispatchers.IO)

    fun updateMembers(members: List<Member>) = localSource.updateMembers(members)
}
