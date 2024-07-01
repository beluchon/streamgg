// use an integer for version numbers
version = 1

cloudstream {
    language = "fr"
    // All of these properties are optional, you can safely remove them

    description = "Site de Streaming 100% Gratuit et Français. Découvrez notre catalogue exclusif de Films et Séries en qualité HD. Rejoignez notre communauté pour profiter d'un divertissement illimité et d'une expérience de streaming VF et VOSTFR de qualité."

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

    iconUrl = "https://sadisflix.cyou/uploads/system_logo/favicon_656c5b99516dd.png"
}