package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.title.ClientSetActionBarTextPacket
import net.cakemc.protocol.protocol.packets.client.title.ClientSetSubtitleTextPacket
import net.cakemc.protocol.protocol.packets.client.title.ClientSetTitleTextPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*

class TitleTranslator(
     itemStackHelper: ItemStackHelper,
     componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSetTitleTextPacket) {
            packet.baseComponent = componentHelper.translateComponent(
                player, packet.baseComponent
            )
            return packet
        }
        if (packet is ClientSetSubtitleTextPacket) {
            packet.baseComponent = componentHelper.translateComponent(
                player, packet.baseComponent
            )
            return packet
        }
        if (packet is ClientSetActionBarTextPacket) {
            packet.baseComponent = componentHelper.translateComponent(
                player, packet.baseComponent
            )
            return packet
        }
        return packet
    }
}