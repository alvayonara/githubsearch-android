package com.alvayonara.githubsearch.core.data.source.remote

data class Resource<out T>(
    val status: Status,
    val body: T? = null,
    val throwable: Throwable? = null
) {
    enum class Status {
        SUCCESS, ERROR
    }

    companion object {
        @JvmStatic
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                body = data
            )
        }

        @JvmStatic
        @JvmOverloads
        fun <T> error(data: Throwable? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                throwable = data
            )
        }
    }
}