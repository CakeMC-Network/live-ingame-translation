package net.cakemc.translator.transformation

import net.cakemc.mc.lib.game.ServerLink
import net.cakemc.mc.lib.game.entity.player.ListEntry
import net.cakemc.mc.lib.game.text.test.api.ChatColor
import net.cakemc.mc.lib.game.text.test.api.chat.BaseComponent
import net.cakemc.mc.lib.game.text.test.api.chat.TextComponent
import net.cakemc.translator.TranslationReplacer
import java.util.*

class ComponentHelper(var translatorRegistry: TranslationReplacer) {

    fun translateComponent(player: UUID, component: BaseComponent): BaseComponent {
        val titlePane = component.toPlainText()

        if (titlePane.contains("\\")) {
            return TextComponent(component.toPlainText())
        }

        if (!translatorRegistry.isKeyPresent(titlePane)) {
            val text = component.toPlainText().replace("\\", "")
            return TextComponent(text)
        }

        val translated = translatorRegistry.findTranslation(player, titlePane)
        return TextComponent(translated)
    }

    fun translateText(player: UUID, text: String): String {
        if (!translatorRegistry.isKeyPresent(text))
            return text.replace("\\", "")

        val translated = translatorRegistry.findTranslation(player, text)
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