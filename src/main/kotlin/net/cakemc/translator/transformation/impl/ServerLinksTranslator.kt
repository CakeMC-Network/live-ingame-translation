package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.ServerLink
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.ClientServerLinksPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*
import kotlin.collections.ArrayList

class ServerLinksTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {
    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientServerLinksPacket) {
            val serverLinks = ArrayList<ServerLink>();
            for (link in packet.links) {
                val translatedLink = componentHelper.translateServerLink(player, link)
                serverLinks.add(translatedLink)
            }

            packet.links = serverLinks.toTypedArray()
            return packet
        }
        return packet
    }


}