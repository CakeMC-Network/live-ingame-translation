package net.cakemc.translator

import net.cakemc.mc.lib.AbstractServer

class TranslatorHook {

    // ClientSetPlayerTeamPacket
    // X ClientSetScorePacket
    // X ClientSetDisplayObjectivePacket
    //
    // X ClientSetActionBarTextPacket
    // X ClientSetSubtitleTextPacket
    // X ClientSetTitleTextPacket
    //
    // X ClientServerLinksPacket -> ServerLink Utils
    //
    // X ClientTabListPacket
    //
    // X ClientPlayerInfoUpdatePacket -> ListEntry Utils
    //
    // X ClientSetPlayerInventoryPacket (items)
    // X ClientSetCursorItemPacket (item)
    // X ClientOpenScreenPacket (title)
    // X ClientContainerSetSlotPacket (item)
    // X ClientContainerSetContentPacket (items, carried)
    //
    // X ClientSetEquipmentPacket -> EquipmentSlot Utils
    //
    // ------------------------------------
    // X ClientSetEntityDataPacket
    //        - meta-data
    // 		- all components


    fun hook(abstractServer: AbstractServer) {

    }

}