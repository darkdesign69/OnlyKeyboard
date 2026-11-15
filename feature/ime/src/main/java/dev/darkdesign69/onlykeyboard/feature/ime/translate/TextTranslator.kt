package dev.darkdesign69.onlykeyboard.feature.ime.translate

import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.common.model.DownloadConditions

/**
 * Translates text with Google ML Kit on-device translation.
 * The language of the text is detected automatically; the language model is downloaded once
 * (needs internet the first time) and after that translation works offline.
 * Callbacks run on the main thread.
 */
object TextTranslator {

    fun translate(
        text: String,
        targetTag: String,
        fallbackSourceTag: String,
        onDone: (result: String?, error: String?) -> Unit
    ) {
        val identifier = LanguageIdentification.getClient()
        identifier.identifyLanguage(text)
            .addOnSuccessListener { detected ->
                val sourceTag = if (detected == "und") fallbackSourceTag else detected
                run(text, sourceTag, targetTag, onDone)
            }
            .addOnFailureListener {
                run(text, fallbackSourceTag, targetTag, onDone)
            }
    }

    private fun run(
        text: String,
        sourceTag: String,
        targetTag: String,
        onDone: (String?, String?) -> Unit
    ) {
        val source = TranslateLanguage.fromLanguageTag(sourceTag)
        val target = TranslateLanguage.fromLanguageTag(targetTag)
        if (source == null || target == null) {
            onDone(null, "This language is not supported")
            return
        }
        if (source == target) {
            onDone(null, "Text is already in the target language")
            return
        }
        val translator = Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(source)
                .setTargetLanguage(target)
                .build()
        )
        translator.downloadModelIfNeeded(DownloadConditions.Builder().build())
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { result ->
                        translator.close()
                        onDone(result, null)
                    }
                    .addOnFailureListener {
                        translator.close()
                        onDone(null, "Translation failed")
                    }
            }
            .addOnFailureListener {
                translator.close()
                onDone(null, "Could not download the language. Check your internet and try again")
            }
    }
}
