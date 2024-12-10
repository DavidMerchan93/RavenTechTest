package com.davidmerchan.raventechtest

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Home : Screens

    @Serializable
    data class Detail(val title: String, val url: String) : Screens
}
