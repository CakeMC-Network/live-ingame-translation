package net.cakemc.translator.transformation

import net.cakemc.mc.lib.network.AbstractPacket
import java.util.*

abstract class PacketLanguageTranslator(
    val itemStackHelper: ItemStackHelper,
    val componentHelper: ComponentHelper
) {

    abstract fun translate(player: UUID, packet: AbstractPacket): AbstractPacket

}