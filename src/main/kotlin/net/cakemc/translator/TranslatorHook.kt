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
    // ClientServerLinksPacket -> ServerLink Utils
    //
    // ClientTabListPacket
    //
    // ClientPlayerInfoUpdatePacket -> ListEntry Utils
    //
    // ClientSetPlayerInventoryPacket (items)
    // ClientSetCursorItemPacket (item)
    // ClientOpenScreenPacket (title)
    // ClientContainerSetSlotPacket (item)
    // ClientContainerSetContentPacket (items, carried)
    //
    // ClientSetEquipmentPacket -> EquipmentSlot Utils
    //
    // ------------------------------------
    // ClientSetEntityDataPacket
    //        - meta-data
    // 		- all components


    fun hook(abstractServer: AbstractServer) {

    }

}