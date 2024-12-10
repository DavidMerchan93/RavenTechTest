package com.davidmerchan.network.api

import com.davidmerchan.network.model.ArticlesResponse
import retrofit2.http.GET

interface ArticlesApi {
    @GET(ApiEndpoints.SEARCH_MOBILE)
    suspend fun getArticles(): ArticlesResponse
}
