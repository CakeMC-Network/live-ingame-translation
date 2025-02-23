package net.cakemc.translator

import net.cakemc.translator.repository.LanguageRepository
import java.util.*
import java.util.regex.Pattern

class DefaultTranslationReplacer(
    val repository: LanguageRepository
) : TranslationReplacer {

    private val keyPattern: Pattern = Pattern.compile("\\b[a-zA-Z0-9_]+(?:\\.[a-zA-Z0-9_]+)+\\b")
    private val numberPattern: Pattern = Pattern.compile("^-?\\d*\\.?\\d+$")
    private val singleNumberPattern: Pattern = Pattern.compile("[0-9].*")
    private val placeholderPattern: Pattern = Pattern.compile("\\{(\\d+)}")
    private val argumentPattern: Pattern = Pattern.compile("\\[(.*?)]$")

    override fun isKeyPresent(key: String): Boolean {
        // Optimized by using a single check over the input
        return key.split(" ").any { keyPattern.matcher(it).matches() &&
                it.length > 2 &&
                !singleNumberPattern.matcher(it).matches() }
    }

    override fun findTranslation(uuid: UUID, input: String): String {
        val (key, arguments) = parseKeyAndArguments(input)
        if (isSingleTranslationKey(key)) {
            return replacePlaceholders(translateKey(uuid, key), arguments) ?: key
        }

        val builder = StringBuilder()
        val splitKeys = key.split(" ")

        for (string in splitKeys) {
            if (!isSingleTranslationKey(string)) {
                builder.append(string).append(" ")
                continue
            }

            val translation = translateKey(uuid, string)
            builder.append(replacePlaceholders(translation, arguments) ?: string).append(" ")
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

    private fun replacePlaceholders(translation: String?, arguments: List<String>): String? {
        if (translation.isNullOrEmpty()) return translation

        val matcher = placeholderPattern.matcher(translation)
        val result = StringBuffer()

        while (matcher.find()) {
            val index = matcher.group(1).toIntOrNull()
            val replacement = if (index != null && index < arguments.size) {
                arguments[index]
            } else {
                matcher.group(0) // Leave placeholder as is if no argument is provided
            }
            matcher.appendReplacement(result, replacement)
        }
        matcher.appendTail(result)
        return result.toString()
    }

    private fun parseKeyAndArguments(input: String): Pair<String, List<String>> {
        val matcher = argumentPattern.matcher(input)
        return if (matcher.find()) {
            val key = input.substring(0, matcher.start()).trim()
            val arguments = matcher.group(1).split(",").map { it.trim() }
            Pair(key, arguments)
        } else {
            Pair(input, emptyList())
        }
    }

    private fun StringBuilder.trimEnd(): String {
        if (this.isNotEmpty() && this.last() == ' ') {
            this.setLength(this.length - 1)
        }
        return this.toString()
    }

}