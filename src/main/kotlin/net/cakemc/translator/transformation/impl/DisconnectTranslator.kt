package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.ClientDisconnectPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*

class DisconnectTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {
    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientDisconnectPacket) {
            return ClientDisconnectPacket(componentHelper.translateComponent(player, packet.message))
        }
        return packet
    }

}