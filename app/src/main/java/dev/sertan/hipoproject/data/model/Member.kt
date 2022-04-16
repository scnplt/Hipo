package dev.sertan.hipoproject.data.model

import com.squareup.moshi.Json

internal data class Member(
    val name: String,
    val age: Byte,
    val location: String,
    val github: String,
    @Json(name = "hipo")
    val hipoInfo: HipoInfo
)
