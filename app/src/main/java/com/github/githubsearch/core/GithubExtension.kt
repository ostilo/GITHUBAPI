package com.github.githubsearch.core

object GithubExtension {
    fun Exception.getStatusCode(): Int {
        return when (this) {
            is retrofit2.HttpException -> code()
            is java.net.SocketTimeoutException -> -1
            is java.io.IOException -> -2
            else -> {
                val message = this.message ?: ""
                try {
                    message.filter { it.isDigit() }
                        .takeIf { it.length == 3 }
                        ?.toInt() ?: -3
                } catch (e: NumberFormatException) {
                    -3
                }
            }
        }
    }

    fun Int.getStatusMessage() : String {
        return when (this){
             -1 -> Constants.REQUEST_TIMEOUT
             -2 -> Constants.IO_EXCEPTION
             422 -> Constants.VALIDATION_FAILED
             503 -> Constants.SERVICE_UNAVAILABLE
             else -> Constants.DEFAULT_ERROR_MESSAGE
        }
    }
}