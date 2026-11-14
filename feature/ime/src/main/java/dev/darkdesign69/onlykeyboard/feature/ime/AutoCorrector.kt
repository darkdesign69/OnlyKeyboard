package dev.darkdesign69.onlykeyboard.feature.ime

/**
 * Conservative single-edit spelling corrector used by the "Auto-correction" setting.
 * A word is only corrected when it is NOT a dictionary word and exactly one edit
 * (swap, wrong letter, extra letter, missing letter) produces a dictionary word.
 */
internal object AutoCorrector {
    private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    fun correct(word: String, isWord: (String) -> Boolean): String? {
        if (word.length < 3 || word.length > 20) return null
        // Only plain lowercase latin words are corrected (names / acronyms / camelCase stay untouched)
        if (!word.all { it in 'a'..'z' }) return null
        if (isWord(word)) return null

        // 1. swapped neighbouring letters (e.g. "teh" -> "the")
        for (i in 0 until word.length - 1) {
            if (word[i] == word[i + 1]) continue
            val chars = word.toCharArray()
            val tmp = chars[i]
            chars[i] = chars[i + 1]
            chars[i + 1] = tmp
            val candidate = String(chars)
            if (isWord(candidate)) return candidate
        }
        // 2. one wrong letter
        for (i in word.indices) {
            for (c in ALPHABET) {
                if (c == word[i]) continue
                val candidate = word.substring(0, i) + c + word.substring(i + 1)
                if (isWord(candidate)) return candidate
            }
        }
        // 3. one extra letter
        for (i in word.indices) {
            val candidate = word.removeRange(i, i + 1)
            if (candidate.length >= 2 && isWord(candidate)) return candidate
        }
        // 4. one missing letter
        for (i in 0..word.length) {
            for (c in ALPHABET) {
                val candidate = word.substring(0, i) + c + word.substring(i)
                if (isWord(candidate)) return candidate
            }
        }
        return null
    }

    fun applyCase(corrected: String, original: String): String {
        return when {
            original.length > 1 && original == original.uppercase() -> corrected.uppercase()
            original.isNotEmpty() && original[0].isUpperCase() -> corrected.replaceFirstChar { it.uppercaseChar() }
            else -> corrected
        }
    }
}
