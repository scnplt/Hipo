package dev.sertan.hipoproject.data.source

import dev.sertan.hipoproject.data.model.ServiceResponse
import retrofit2.http.GET



internal interface HipoService {
    @GET(GET_ALL_MEMBERS_PATH)
    suspend fun getAllMembers(): ServiceResponse?

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/"
        private const val GET_ALL_MEMBERS_PATH =
            "artizco/a957d4e0af6f9d35048808e7200ea076/raw/cedc9e9087cf7288eb733839ff9adf319bb737b3/hipo.json"
    }
}
