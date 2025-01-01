package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.scoreboard.ClientSetDisplayObjectivePacket
import net.cakemc.protocol.protocol.packets.client.scoreboard.ClientSetScorePacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator

class ScoreboardTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: Player, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSetScorePacket) {
            packet.display = componentHelper.translateComponent(
                player, packet.display
            )
            return packet
        }
        if (packet is ClientSetDisplayObjectivePacket) {
            packet.name = componentHelper.translateText(
                player, packet.name
            )
            return packet
        }
        return packet
    }

}