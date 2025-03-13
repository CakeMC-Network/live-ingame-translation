package net.cakemc.translator.transformation.impl

import net.cakemc.mc.lib.game.entity.metadata.MetaDataEntry
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
            for (entry in ArrayList(packet.changes.metadata.entries)) {
                if (entry.value.type == MetaDataType.STRING_TYPE) {
                    packet.changes.metadata.remove(entry.key)

                    val type: String = entry.value.value as String;
                    val translated = componentHelper.translateText(
                        player, type
                    )

                    packet.changes.metadata.put(entry.key, MetaDataEntry(MetaDataType.STRING_TYPE, translated))
                }
                if (entry.value.type == MetaDataType.OPT_CHAT_TYPE && entry.value.value != null) {
                    packet.changes.metadata.remove(entry.key)

                    val type: BaseComponent = entry.value.value as BaseComponent
                    val translated =  componentHelper.translateComponent(
                        player, type
                    )

                    packet.changes.metadata.put(entry.key, MetaDataEntry(MetaDataType.CHAT_TYPE, translated))
                }
                if (entry.value.type == MetaDataType.CHAT_TYPE) {
                    packet.changes.metadata.remove(entry.key)

                    val type: BaseComponent = entry.value.value as BaseComponent
                    val translated = componentHelper.translateComponent(
                        player, type
                    )

                    packet.changes.metadata.put(entry.key, MetaDataEntry(MetaDataType.CHAT_TYPE, translated))
                }

            }
            return packet
        }
        return packet
    }

}