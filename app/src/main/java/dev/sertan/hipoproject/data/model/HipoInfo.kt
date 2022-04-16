package dev.sertan.hipoproject.data.model

import com.squareup.moshi.Json

internal data class HipoInfo(
    val position: String,
    @Json(name = "years_in_hipo")
    val yearsInHipo: Byte
)
