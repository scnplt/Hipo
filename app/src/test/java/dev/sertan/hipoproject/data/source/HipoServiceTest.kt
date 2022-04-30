package dev.sertan.hipoproject.data.source

import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.sertan.hipoproject.data.model.HipoInfo
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.data.model.ServiceResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.nio.charset.StandardCharsets

internal class HipoServiceTest {
    private lateinit var webServer: MockWebServer
    private lateinit var service: HipoService
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val defaultServiceResponse = ServiceResponse(
        company = "Hipo",
        team = "Android",
        members = listOf(
            Member(
                name = "Sertan Canpolat",
                age = 22,
                location = "Elazığ",
                github = "scnplt",
                hipoInfo = HipoInfo("Intern", 0)
            )
        )
    )

    @Before
    fun setUp() {
        webServer = MockWebServer()

        val converterFactory = MoshiConverterFactory.create(moshi)
        service = Retrofit.Builder()
            .baseUrl(webServer.url("/"))
            .addConverterFactory(converterFactory.asLenient())
            .build().create(HipoService::class.java)
    }

    @After
    fun teardown() {
        webServer.shutdown()
    }

    @Test
    fun jsonAdapter() {
        val adapter = moshi.adapter(ServiceResponse::class.java)
        val json = "{\"company\":\"Hipo\",\"team\":\"Android\"," +
                "\"members\":[{\"name\":\"Sertan Canpolat\",\"age\":22,\"location\":\"Elazığ\",\"github\":\"scnplt\"," +
                "\"hipo\":{\"position\":\"Intern\",\"years_in_hipo\":0}}]}"
        Truth.assertThat(adapter.toJson(defaultServiceResponse)).isEqualTo(json)
        Truth.assertThat(adapter.fromJson(json)).isEqualTo(defaultServiceResponse)
    }

    @Test
    fun getMembersSuccess() {
        setMockWebServer("service-response-200.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = service.getMembers()
            Truth.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
            Truth.assertThat(response.body()).isEqualTo(defaultServiceResponse)
        }
    }

    @Test
    fun getMembersFailure() {
        setMockWebServer("service-response-404.json", HttpURLConnection.HTTP_NOT_FOUND)
        runBlocking {
            val response = service.getMembers()
            Truth.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
            Truth.assertThat(response.body()).isNull()
        }
    }

    private fun setMockWebServer(responsePath: String, statusCode: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(responsePath)
        inputStream?.source()?.buffer()?.let {
            webServer.enqueue(
                MockResponse()
                    .setResponseCode(statusCode)
                    .setBody(it.readString(StandardCharsets.UTF_8))
            )
        }
    }
}
