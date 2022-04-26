package dev.sertan.hipoproject.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class HipoInfo(
    val position: String,
    @Json(name = "years_in_hipo")
    val yearsInHipo: Byte
) : Parcelable
