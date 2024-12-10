package com.davidmerchan.core

interface AppLogger {
    fun log(error: Throwable)
    fun log(message: String)
}
