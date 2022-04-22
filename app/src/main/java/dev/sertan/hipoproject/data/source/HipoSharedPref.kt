package dev.sertan.hipoproject.data.source

import android.content.Context
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.model.ServiceResponse
import dev.sertan.hipoproject.util.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val KEY = "hipo"

internal class HipoSharedPref constructor(
    @ApplicationContext private val context: Context,
    private val adapter: JsonAdapter<ServiceResponse>
) {
    private val sharedPref = context
        .getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        .apply {
            registerOnSharedPreferenceChangeListener { _, key ->
                if (key == KEY) _members.value = getMembers()
            }
        }

    private val _members: MutableStateFlow<State<List<Member>>> = MutableStateFlow(getMembers())
    val members: StateFlow<State<List<Member>>> get() = _members

    private fun getMembers(): State<List<Member>> =
        getData()?.let { State.loaded(it.members) } ?: State.error()

    private fun getData() = sharedPref.getString(KEY, null)?.let { adapter.fromJson(it) }

    fun saveResponse(response: ServiceResponse?) {
        response ?: return
        val json = adapter.toJson(response)
        sharedPref.edit().putString(KEY, json).apply()
    }

    fun addMember(member: Member) {
        val response = getData() ?: return
        response.members.add(member)
        saveResponse(response)
    }
}
