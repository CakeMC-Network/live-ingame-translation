package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.creature.Player
import net.cakemc.mc.lib.game.entity.metadata.MetaDataType
import net.cakemc.mc.lib.game.text.test.api.chat.BaseComponent
import net.cakemc.mc.lib.network.AbstractPacket
import net.cakemc.protocol.protocol.packets.client.entity.ClientSetEntityDataPacket
import net.cakemc.translator.transformation.ComponentHelper
import net.cakemc.translator.transformation.ItemStackHelper
import net.cakemc.translator.transformation.PacketLanguageTranslator
import java.util.*

class EntityMetaDataTranslator(
    itemStackHelper: ItemStackHelper,
    componentHelper: ComponentHelper
): PacketLanguageTranslator(
    itemStackHelper,
    componentHelper
) {

    override fun translate(player: UUID, packet: AbstractPacket): AbstractPacket {
        if (packet is ClientSetEntityDataPacket) {
            for (entry in packet.changes.metadata.entries) {
                if (entry.value.type == MetaDataType.STRING_TYPE) {
                    val type: String = entry.value.value as String;
                    entry.value.value = componentHelper.translateText(
                        player, type
                    ) as Nothing?
                }
                if (entry.value.type == MetaDataType.OPT_CHAT_TYPE &&
                    !entry.value.value.javaClass.equals(Void::javaClass)) {

                    val type: BaseComponent = entry.value.value as BaseComponent
                    entry.value.value = componentHelper.translateComponent(
                        player, type
                    ) as Nothing?
                }
                if (entry.value.type == MetaDataType.CHAT_TYPE) {
                    val type: BaseComponent = entry.value.value as BaseComponent
                    entry.value.value = componentHelper.translateComponent(
                        player, type
                    ) as Nothing?
                }

            }
            return packet
        }
        return packet
    }

}