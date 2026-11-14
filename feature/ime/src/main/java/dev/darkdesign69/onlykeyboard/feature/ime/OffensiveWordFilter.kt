package dev.darkdesign69.onlykeyboard.feature.ime

/**
 * Small built-in list of offensive English words that are never offered as suggestions
 * when "Block offensive words" is enabled. Exact matches only (so "cockpit" etc. are safe).
 */
internal object OffensiveWordFilter {
    private val baseWords = listOf(
        "fuck", "shit", "bitch", "asshole", "bastard", "cunt", "dick", "slut", "whore",
        "nigger", "nigga", "faggot", "pussy", "cock", "motherfucker", "bullshit",
        "douche", "wanker", "twat", "porn", "dickhead", "jackass", "dumbass"
    )
    private val suffixes = listOf("", "s", "es", "ing", "ed", "er", "ers", "y", "in")

    private val blocked: Set<String> = buildSet {
        for (w in baseWords) for (s in suffixes) add(w + s)
    }

    fun isOffensive(word: String): Boolean {
        val w = word.trim().lowercase()
        return w.isNotEmpty() && w in blocked
    }
}
