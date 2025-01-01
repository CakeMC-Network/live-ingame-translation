package net.cakemc.translator.transformation

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.network.AbstractPacket

abstract class PacketLanguageTranslator(
    val itemStackHelper: ItemStackHelper,
    val componentHelper: ComponentHelper
) {

    abstract fun translate(player: Player, packet: AbstractPacket): AbstractPacket

}