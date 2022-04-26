package dev.sertan.hipoproject.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    val name: String,
    val age: Byte,
    val location: String,
    val github: String,
    @Json(name = "hipo")
    val hipoInfo: HipoInfo? = null
) : Parcelable
