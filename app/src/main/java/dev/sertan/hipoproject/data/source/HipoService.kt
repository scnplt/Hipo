package dev.sertan.hipoproject.data.source

import dev.sertan.hipoproject.data.model.ServiceResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HipoService {
    @GET("artizco/$GIST")
    suspend fun getMembers(): Response<ServiceResponse>

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/"
        private const val GIST =
            "a957d4e0af6f9d35048808e7200ea076/raw/42c1ef79d661d14d3af308ca4088f4ea7f94ac45"
    }
}
