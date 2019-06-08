package com.linkedintools.da.web

import com.linkedintools.model.LinkedInConnection
import com.linkedintools.model.LinkedInUserSettings
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API declarations.
 */
interface LinkedInAPI {
    /**
     * A user's LinkedIn settings
     */
    @GET("/psettings/")
    fun getSettings(): Single<LinkedInUserSettings>

    @GET("/voyager/api/relationships/connections?count=40&sortType=RECENTLY_ADDED&")
    fun getConnections(@Query("start") startPos: Int): Observable<List<LinkedInConnection>>

}