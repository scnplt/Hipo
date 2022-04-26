package dev.sertan.hipoproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.repository.HipoRepository
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(private val repo: HipoRepository) : ViewModel() {
    private val serviceResponse = repo.responseState.asLiveData(viewModelScope.coroutineContext)
    val members: LiveData<List<Member>> = Transformations.map(serviceResponse) { it.data?.members }

    fun addMember(member: Member) {
        val newMemberList = (members.value ?: emptyList()) + member
        repo.updateMembers(newMemberList)
    }
}
