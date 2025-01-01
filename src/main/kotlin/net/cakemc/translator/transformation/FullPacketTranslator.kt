package net.cakemc.translator.transformation

import net.cakemc.mc.lib.AbstractServer
import net.cakemc.mc.lib.game.event.EventHandlers
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.events.impl.packet.ServerSendPacketEvent
import net.cakemc.protocol.player.NetworkPlayer
import net.cakemc.translator.transformation.impl.*

class FullPacketTranslator(
    var itemStackHelper: ItemStackHelper,
    var componentHelper: ComponentHelper,
    var abstractServer: AbstractServer,
) {

    var translators = listOf(
        EntityMetaDataTranslator(itemStackHelper, componentHelper),
        EquipmentTranslator(itemStackHelper, componentHelper),
        InventoryTranslator(itemStackHelper, componentHelper),
        MessageTranslator(itemStackHelper, componentHelper),
        PlayerInfoTranslator(itemStackHelper, componentHelper),
        ScoreboardTranslator(itemStackHelper, componentHelper),
        ServerLinksTranslator(itemStackHelper, componentHelper),
        TablistTranslator(itemStackHelper, componentHelper),
        TitleTranslator(itemStackHelper, componentHelper),
    )

    public fun register() {
        abstractServer.eventManager.register(ServerSendPacketEvent::class.java, EventHandlers.IHandler {
            if (it.networkPlayer.currentState != NetworkPlayer.State.GAME)
                return@IHandler

            val playerUUID = it.networkPlayer.playerUUID

            var packet: AbstractPacket = it.packet
            for (translator in translators) {
                packet = translator.translate(
                    playerUUID, it.packet
                )
            }

            it.packet = packet
        })
    }

}