package net.cakemc.translator

import net.cakemc.translator.repository.LanguageRepository
import java.util.*
import java.util.regex.Pattern

class DefaultTranslationReplacer(
    val repository: LanguageRepository
) : TranslationReplacer {

    private val keyPattern: Pattern = Pattern.compile("\\b[a-zA-Z0-9_]+(?:\\.[a-zA-Z0-9_]+)+\\b")
    private val numberPattern: Pattern = Pattern.compile("^-?\\d*\\.?\\d+$")

    override fun isKeyPresent(key: String): Boolean {
        // Optimized by using a single check over the input
        return key.split(" ").any { keyPattern.matcher(it).matches() }
    }


    override fun findTranslation(uuid: UUID, key: String): String {
        if (isSingleTranslationKey(key)) {
            return translateKey(uuid, key) ?: key
        }

        val builder = StringBuilder()
        val splitKeys = key.split(" ")

        for (string in splitKeys) {
            if (!isSingleTranslationKey(string)) {
                builder.append(string).append(" ")
                continue
            }

            builder.append(translateKey(uuid, string) ?: string).append(" ")
        }
        // Remove trailing space
        return builder.trimEnd()
    }

    private fun isSingleTranslationKey(key: String): Boolean {
        return keyPattern.matcher(key).matches() && !numberPattern.matcher(key).matches()
    }

    private fun translateKey(uuid: UUID, key: String): String? {
        val keySplit = key.split(".")
        if (keySplit.size < 2) return null

        val category = keySplit[0]
        val translations = repository.resolveLanguage(uuid, category)
        return translations.resolveForKey(key)
    }

    private fun StringBuilder.trimEnd(): String {
        if (this.isNotEmpty() && this.last() == ' ') {
            this.setLength(this.length - 1)
        }
        return this.toString()
    }

}