package dev.sertan.hipoproject.data.repo

import dev.sertan.hipoproject.data.source.HipoService
import dev.sertan.hipoproject.data.source.HipoSharedPref
import dev.sertan.hipoproject.util.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HipoRepository @Inject constructor(
    private val service: HipoService,
    private val sharedPref: HipoSharedPref
) {
    private var updateJob: Job? = null
    val members = sharedPref.members

    init {
        if (members.value !is State.Loaded) updateMembersFromService()
    }

    private fun updateMembersFromService() {
        updateJob?.cancel()
        updateJob = CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAllMembers()
            sharedPref.saveResponse(response)
        }
    }
}
