package dev.sertan.hipoproject.data.source

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.model.ServiceResponse
import dev.sertan.hipoproject.util.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class LocalDataSource @Inject constructor(
    @ApplicationContext context: Context,
    private val jsonAdapter: JsonAdapter<ServiceResponse>
) {
    private val sharedPrefListener = OnSharedPreferenceChangeListener { _, key ->
        if (key == RESPONSE_KEY) _serviceResponse.value = getResponse()
    }
    private val sharedPref = context
        .getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        .apply { registerOnSharedPreferenceChangeListener(sharedPrefListener) }

    private val _serviceResponse = MutableStateFlow(getResponse())
    val serviceResponse: StateFlow<State<ServiceResponse>> = _serviceResponse

    private fun getResponse(): State<ServiceResponse> {
        return try {
            val json = sharedPref.getString(RESPONSE_KEY, null)!!
            val data = jsonAdapter.fromJson(json)!!
            State.success(data)
        } catch (e: Exception) {
            State.failure(e)
        }
    }

    fun saveResponse(serviceResponse: ServiceResponse) {
        val json = jsonAdapter.toJson(serviceResponse)
        sharedPref.edit().putString(RESPONSE_KEY, json).apply()
    }

    fun updateMembers(members: List<Member>) {
        val newServiceResponse = serviceResponse.value.data?.copy(members = members)
            ?: ServiceResponse(members = members)
        val json = jsonAdapter.toJson(newServiceResponse)
        sharedPref.edit().putString(RESPONSE_KEY, json).apply()
    }

    companion object {
        private const val SHARED_PREF = "HIPO"
        private const val RESPONSE_KEY = "HIPO_RESPONSE"
    }
}
