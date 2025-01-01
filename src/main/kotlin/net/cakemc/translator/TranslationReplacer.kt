package net.cakemc.translator

import java.util.UUID

interface TranslationReplacer {

    fun isKeyPresent(key: String): Boolean
    fun findTranslation(uuid: UUID, key: String): String

}