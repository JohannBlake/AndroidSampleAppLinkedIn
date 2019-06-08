package com.linkedintools.da

import androidx.paging.DataSource
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.model.LinkedInUserSettings
import io.reactivex.Observable
import io.reactivex.Single

interface IRepository {

    var cachedLinkedInUserSettings: LinkedInUserSettings?
    var cookie: String?
    var csrfToken: String?
    var firstUsageInitialized: Boolean
    var isRetrievingConnections: Boolean

    fun getLinkedInUserSettings(): Single<LinkedInUserSettings>
    fun getLinkedInConnections(startPos: Int): Observable<List<LinkedInConnection>>
    fun getConnectionsByFirstName(): DataSource.Factory<Int, LinkedInConnection>
    fun storeConnections(connections: List<LinkedInConnection>): Observable<List<LinkedInConnection>>
    fun getConnectionsCount(): Observable<Int>

}