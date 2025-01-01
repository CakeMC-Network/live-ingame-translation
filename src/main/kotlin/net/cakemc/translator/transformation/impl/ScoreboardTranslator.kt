package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.scoreboard.ClientSetDisplayObjectivePacket
import net.cakemc.protocol.protocol.packets.client.scoreboard.ClientSetPlayerTeamPacket
import net.cakemc.protocol.protocol.packets.client.scoreboard.ClientSetScorePacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*

class ScoreboardTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
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
        if (packet is ClientSetPlayerTeamPacket) {
            packet.prefix = componentHelper.translateComponent(
                player, packet.prefix
            )
            packet.suffix = componentHelper.translateComponent(
                player, packet.suffix
            )
            packet.displayName = componentHelper.translateComponent(
                player, packet.displayName
            )
            return packet
        }
        return packet
    }

}