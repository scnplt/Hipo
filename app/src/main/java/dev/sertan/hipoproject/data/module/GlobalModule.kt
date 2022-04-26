package dev.sertan.hipoproject.data.module

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sertan.hipoproject.data.model.ServiceResponse
import dev.sertan.hipoproject.data.source.HipoService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GlobalModule {

    @Provides
    @Singleton
    fun getMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun getJsonAdapter(moshi: Moshi): JsonAdapter<ServiceResponse> {
        return moshi.adapter(ServiceResponse::class.java)
    }

    @Provides
    @Singleton
    fun getHipoService(moshi: Moshi): HipoService {
        val converterFactory = MoshiConverterFactory.create(moshi)
        return Retrofit.Builder()
            .baseUrl(HipoService.BASE_URL)
            .addConverterFactory(converterFactory)
            .build().create(HipoService::class.java)
    }

}
