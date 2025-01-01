package net.cakemc.translator

import net.cakemc.translator.repository.LanguageRepository
import java.util.*
import java.util.regex.Pattern

class DefaultTranslationReplacer(
    val repository: LanguageRepository
) : TranslationReplacer {

    private val keyPattern: Pattern = Pattern.compile("\\b[a-zA-Z0-9_]+(?:\\.[a-zA-Z0-9_]+)+\\b")

    override fun isKeyPresent(key: String): Boolean {
        return key.split(" ").stream().anyMatch { keyPattern.matcher(it).matches() }
    }

    override fun findTranslation(uuid: UUID, key: String): String {
        if (keyPattern.matcher(key).matches()) {
            val keySplit = key.split(".")

            if (keySplit.size == 0 || keySplit.size == 1)
                return key

            val category = keySplit[0]
            val translations = repository.resolveLanguage(
                uuid, category
            )

            return translations.resolveForKey(key)
        }

        val builder = StringBuilder()
        val split = key.split(" ")
        for (string in split) {
            if (!keyPattern.matcher(string).matches()) {
                builder.append(string).append(" ")
                continue
            }

            val keySplit = string.split(".")
            if (keySplit.size == 0 || keySplit.size == 1) {
                builder.append(string).append(" ")
                continue
            }

            val category = keySplit[0]
            val translations = repository.resolveLanguage(
                uuid, category
            )

            val translated = translations
                .resolveForKey(string)

            builder.append(translated).append(" ")
        }
        return builder.toString()
    }

}