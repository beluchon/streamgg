package com.lagradost


import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.LoadResponse.Companion.addTrailer
import com.lagradost.cloudstream3.utils.AppUtils.toJson
import com.lagradost.cloudstream3.utils.AppUtils.tryParseJson
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.nodes.Element


class FrenchStreamProvider : MainAPI() {
    override var mainUrl = "https://www2.hds-streaming.to/"
    override var name = "HDS.to"
    override val hasQuickSearch = false
    override val hasMainPage = true
    override var lang = "fr"
    override val supportedTypes = setOf(TvType.Movie)

    override suspend fun search(query: String): List<SearchResponse> {
        val link = "$mainUrl/search/$query" // search'
        val document =
            app.post(link).document // app.get() permet de télécharger la page html avec une requete HTTP (get)
        val results = document.select("div#dle-content > div.short")


        val allresultshome =
            results.mapNotNull { article ->  // avec mapnotnull si un élément est null, il sera automatiquement enlevé de la liste
                article.toSearchResponse()
            }
        return allresultshome
    }

    override suspend fun load(url: String): LoadResponse {
        val soup = app.get(url).document
        var subEpisodes = listOf<Episode>()
        var dubEpisodes = listOf<Episode>()
        val title = soup.selectFirst("h1#s-title")!!.text().toString()
        val description =
            soup.selectFirst("div#s-desc")!!.text().toString().replace(/^.*?console/, '');
        val poster = soup.selectFirst("div.fposter > img")?.attr("src")
        val tags = soup.select("ul.flist-col > li").getOrNull(1)


        //val rating = soup.select("span[id^=vote-num-id]")?.getOrNull(1)?.text()?.toInt()
            val yearRegex = Regex("""ate de sortie\: (\d*)""")
            val year = yearRegex.find(soup.text())?.groupValues?.get(1)
            val tagsList = tags?.select("a")
                ?.mapNotNull {   // all the tags like action, thriller ...; unused variable
                    it?.text()
                }
            return newMovieLoadResponse(title, url, TvType.Movie, url) {
                this.posterUrl = poster
                this.year = year?.toIntOrNull()
                this.tags = tagsList
                this.plot = description
            }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit,
    ): Boolean {
		
		val servers = app.get(data).document.select("div.tabs-sel a").attr("cid")

        servers.apmap { playerUrl ->
            loadExtractor(playerUrl, mainUrl, subtitleCallback, callback)
        }

        return true
    }


    private fun Element.toSearchResponse(): SearchResponse {

        val posterUrl = fixUrl(select("a.short-poster > img").attr("src"))
        val title = select("div.short-title").text()
        val link = select("a.short-poster").attr("href")
		
            return MovieSearchResponse(
                name = title,
                url = link,
                apiName = title,
                type = TvType.Movie,
                posterUrl = posterUrl,
            )

    }

    data class mediaData(
        @JsonProperty("title") var title: String,
        @JsonProperty("url") val url: String,
    )

    override val mainPage = mainPageOf(
        Pair("film-genre/film-streaming/", "Derniers films ajoutés"),
        Pair("film-genre/action/", "Action"),
        Pair("film-genre/thriller/", "Thriller"),
        Pair("film-genre/drame/", "Drame"),
        Pair("film-genre/comedie/", "Comédie"),
        Pair("film-genre/anime/", "Animation"),
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = mainUrl + request.data + page
        val document = app.get(url).document
        val movies = document.select("div#dle-content > div.short")

        val home =
            movies.map { article ->  // avec mapnotnull si un élément est null, il sera automatiquement enlevé de la liste
                article.toSearchResponse()
            }
        return newHomePageResponse(request.name, home)
    }
}