package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.inventory.item.ItemStack
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.inventory.ClientContainerSetContentPacket
import net.cakemc.protocol.protocol.packets.client.inventory.ClientContainerSetSlotPacket
import net.cakemc.protocol.protocol.packets.client.inventory.ClientOpenScreenPacket
import net.cakemc.protocol.protocol.packets.client.inventory.ClientSetCursorItemPacket
import net.cakemc.protocol.protocol.packets.client.player.ClientSetPlayerInventoryPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*
import kotlin.collections.ArrayList

class InventoryTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSetPlayerInventoryPacket) {
            packet.contents = itemStackHelper.translateItemStack(
                player, packet.contents
            )
            return packet
        }
        if (packet is ClientSetCursorItemPacket) {
            packet.contents = itemStackHelper.translateItemStack(
                player, packet.contents
            )
            return packet
        }
        if (packet is ClientOpenScreenPacket) {
            packet.title = componentHelper.translateComponent(
                player, packet.title
            )
            return packet
        }
        if (packet is ClientContainerSetSlotPacket) {
            packet.item = itemStackHelper.translateItemStack(
                player, packet.item
            )
            return packet
        }
        if (packet is ClientContainerSetContentPacket) {
            packet.carriedItem = itemStackHelper.translateItemStack(
                player, packet.carriedItem
            )
            val itemStacks = ArrayList<ItemStack>()
            for (item in packet.items) {
                itemStacks.add(itemStackHelper.translateItemStack(
                    player, item
                ))
            }
            packet.items = itemStacks.toTypedArray()
            return packet
        }
        return packet
    }

}