package net.cakemc.translator.transformation

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.text.test.api.ChatColor
import net.cakemc.mc.lib.game.text.test.api.chat.BaseComponent
import net.cakemc.mc.lib.game.text.test.api.chat.TextComponent
import net.cakemc.translator.TranslationRegistry

class ComponentHelper(var translatorRegistry: TranslationRegistry) {

    fun translateComponent(player: Player, component: BaseComponent): BaseComponent {
        val titlePane = ChatColor.stripColor(component.toPlainText())
        if (!translatorRegistry.isKeyPresent(titlePane))
            return component

        val translated = translatorRegistry.findTranslation(player.uuid, titlePane)
        return TextComponent(translated)
    }

    fun translateText(player: Player, text: String): String {
        val titlePane = ChatColor.stripColor(text)
        if (!translatorRegistry.isKeyPresent(titlePane))
            return text

        val translated = translatorRegistry.findTranslation(player.uuid, titlePane)
        return translated
    }

}