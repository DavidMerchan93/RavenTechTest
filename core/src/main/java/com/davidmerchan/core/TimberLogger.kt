package com.davidmerchan.core

import timber.log.Timber

class TimberLogger: AppLogger {
    override fun log(message: String) {
        Timber.log(0, message)
    }

    override fun log(error: Throwable) {
        Timber.d(error)
    }
}
