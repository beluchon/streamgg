package com.lagradost

import com.fasterxml.jackson.annotation.JsonProperty
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import com.lagradost.cloudstream3.utils.AppUtils.toJson
import com.lagradost.cloudstream3.utils.AppUtils.tryParseJson
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import kotlin.collections.ArrayList
import com.lagradost.cloudstream3.network.CloudflareKiller
import com.lagradost.nicehttp.NiceResponse
import java.util.*

import android.util.Log

class WiflixProvider : MainAPI() {
    override var mainUrl = "https://wiflix.date/"
    override var name = "Wiflix"
    override val hasQuickSearch = false // recherche rapide (optionel, pas vraimet utile)
    override val hasMainPage = true // page d'accueil (optionel mais encoragé)
    override var lang = "fr" // fournisseur est en francais
    override val supportedTypes =
        setOf(TvType.Movie, TvType.TvSeries) // series, films
    private val interceptor = CloudflareKiller()
    private var isNotInit = true
    suspend fun initMainUrl() {
        try {
            Log.d("MyTag", "This is a debug message") // Debug log message
        } catch (e: Exception) { // url changed
            Log.d("MyTag", "This is a debug message")
        }

    }


}

