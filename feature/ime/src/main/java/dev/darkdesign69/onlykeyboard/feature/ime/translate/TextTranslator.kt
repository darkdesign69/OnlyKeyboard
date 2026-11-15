package dev.darkdesign69.onlykeyboard.feature.ime.translate

/** Translation was removed to keep the app small; this stub keeps the old callers compiling. */
object TextTranslator {
    fun translate(
        text: String,
        targetTag: String,
        fallbackSourceTag: String,
        onDone: (result: String?, error: String?) -> Unit
    ) {
        onDone(null, "Translation is not available")
    }
}
