package dev.sertan.hipoproject.data.model

internal data class ServiceResponse(
    val company: String,
    val team: String,
    val members: ArrayList<Member>
)
