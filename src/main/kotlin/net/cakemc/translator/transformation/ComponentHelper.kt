package net.cakemc.translator.transformation

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.ServerLink
import net.cakemc.mc.lib.game.entity.player.ListEntry
import net.cakemc.mc.lib.game.text.test.api.ChatColor
import net.cakemc.mc.lib.game.text.test.api.chat.BaseComponent
import net.cakemc.mc.lib.game.text.test.api.chat.TextComponent
import net.cakemc.translator.TranslationRegistry
import java.util.*

class ComponentHelper(var translatorRegistry: TranslationRegistry) {

    fun translateComponent(player: UUID, component: BaseComponent): BaseComponent {
        val titlePane = ChatColor.stripColor(component.toPlainText())
        if (!translatorRegistry.isKeyPresent(titlePane))
            return component

        val translated = translatorRegistry.findTranslation(player, titlePane)
        return TextComponent(translated)
    }

    fun translateText(player: UUID, text: String): String {
        val titlePane = ChatColor.stripColor(text)
        if (!translatorRegistry.isKeyPresent(titlePane))
            return text

        val translated = translatorRegistry.findTranslation(player, titlePane)
        return translated
    }

    fun translateServerLink(player: UUID, link: ServerLink): ServerLink {
        val baseComponent = link.unknownType
        return ServerLink(link.type, translateComponent(player, baseComponent), link.link)
    }

    fun translateInfoEntry(player: UUID, infoEntry: ListEntry): ListEntry {
        infoEntry.displayName = translateComponent(player, infoEntry.displayName)
        return infoEntry
    }

}