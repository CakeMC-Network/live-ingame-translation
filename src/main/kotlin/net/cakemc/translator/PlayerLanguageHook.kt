package net.cakemc.translator

import java.util.UUID

interface PlayerLanguageHook {

    fun resolveLanguage(player: UUID): String?

}