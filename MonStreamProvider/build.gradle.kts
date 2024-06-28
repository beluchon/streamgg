// use an integer for version numbers
version = 3


cloudstream {
    language = "fr"
    // All of these properties are optional, you can safely remove them

    description = "MonStream - site exclusif de streaming Francophone où 95% de films et series en streaming sont dispo en qualité HD, en VF et VOSTFR et gratuitement."
    authors = listOf("zzikozz")

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     * */
    status = 1 // will be 3 if unspecified
    tvTypes = listOf(
        "TvSeries",
		"Movie",
    )

    iconUrl = "https://monstream.app/templates/monstream_rip/images/favicon.png"
}