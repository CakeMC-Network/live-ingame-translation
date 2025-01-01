package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.entity.player.ListEntry
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.ClientPlayerInfoUpdatePacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*
import kotlin.collections.ArrayList

class PlayerInfoTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientPlayerInfoUpdatePacket) {
            val entries = ArrayList<ListEntry>()
            for (entry in packet.entries) {
                val translatedEntry = componentHelper.translateInfoEntry(
                    player, entry
                )
                entries.add(translatedEntry)
            }

            packet.entries = entries.toTypedArray()
            return packet
        }
        return packet
    }

}