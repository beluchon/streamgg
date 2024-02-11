package com.lagradost
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.getQualityFromName
import java.util.regex.Pattern


open class UqloadExtractor : ExtractorApi() {
    override var name = "Uqload"
    override var mainUrl = "https://uqload.to/"
    override val requiresReferer = true

    // AhAh j'ai la logique mais pas le temps de faire le code soon
    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink>? {
        val response0 = app.get(url).text // HTML de la page Uqload pour rechercher le lien de téléchargement
        val linkPattern = Pattern.compile("\"(https://uqload.com/download/[^\"]+)\"")
        val linkMatcher = linkPattern.matcher(response0)
        val downloadLink = if (linkMatcher.find()) {
            linkMatcher.group(1)
        } else {
            return null
        }
        return listOf(
                ExtractorLink(
                        downloadLink,
                        name,
                        downloadLink,
                        mainUrl,
                        getQualityFromName(null),
                        false
                )
        )
    }
}