package net.cakemc.translator

import net.cakemc.mc.lib.AbstractServer
import net.cakemc.translator.repository.LanguageRepository
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.FullPacketTranslator
import net.cakemc.translator.transformation.ItemStackHelper
import java.nio.file.Path

class TranslationSystem(
    val languageFolder: Path
) {

    lateinit var repository: LanguageRepository
    lateinit var translationReplacer: TranslationReplacer
    lateinit var componentHelper: ComponentHelper
    lateinit var itemHelper: ItemStackHelper
    var initialized = false

    fun initialize(playerHook: PlayerLanguageHook?) {
        if (playerHook == null)
            throw IllegalStateException("missing player-translator-hook!")

        repository = LanguageRepository(languageFolder, playerHook)
        translationReplacer = DefaultTranslationReplacer(repository)

        componentHelper = ComponentHelper(translationReplacer)
        itemHelper = ItemStackHelper(translationReplacer, componentHelper)

        initialized = true
    }

    fun hookFor(abstractServer: AbstractServer) {
        if (!initialized)
            throw IllegalStateException("translation system not initialized!")

        val packetTranslator = FullPacketTranslator(
            itemHelper, componentHelper, abstractServer
        )

        packetTranslator.register()
    }

}