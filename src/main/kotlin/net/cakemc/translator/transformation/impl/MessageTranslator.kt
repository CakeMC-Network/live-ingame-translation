package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.game.text.test.api.chat.TextComponent
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.ClientSystemChatPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*

class MessageTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSystemChatPacket) {
            packet.component = componentHelper.translateComponent(
                player, TextComponent(
                    packet.component.toPlainText()
                        .replace("\\", "")
                )
            )
            return packet
        }
        return packet
    }

}