package dev.sertan.hipoproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.repository.HipoRepository
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(private val repo: HipoRepository) : ViewModel() {
    val serviceResponse = repo.responseState.asLiveData(viewModelScope.coroutineContext)

    fun addMember(member: Member) {
        val newMemberList = (serviceResponse.value?.data?.members ?: emptyList()) + member
        repo.updateMembers(newMemberList)
    }
}
