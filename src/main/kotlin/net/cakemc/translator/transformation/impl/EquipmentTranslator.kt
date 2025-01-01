package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.inventory.item.EquipmentSlot
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.entity.ClientSetEquipmentPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*
import kotlin.collections.ArrayList

class EquipmentTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSetEquipmentPacket) {
            val equipments = ArrayList<EquipmentSlot>()
            for (item in packet.items) {
                val itemStack = itemStackHelper.translateItemStack(
                    player, item.stack
                )
                equipments.add(EquipmentSlot(item.group, itemStack))
            }
            packet.items = equipments.toTypedArray()
            return packet
        }
        return packet
    }

}