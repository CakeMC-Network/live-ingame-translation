package net.cakemc.translator.transformation

import net.cakemc.mc.lib.game.inventory.item.ItemStack
import net.cakemc.mc.lib.game.inventory.item.component.DataComponentTypes
import net.cakemc.mc.lib.game.text.test.api.chat.BaseComponent
import net.cakemc.translator.TranslationReplacer
import java.util.*
import kotlin.collections.ArrayList

class ItemStackHelper(
    var registry: TranslationReplacer,
    var componentHelper: ComponentHelper
) {

    val componentTypesToTranslate = listOf(
        DataComponentTypes.ITEM_NAME,
        DataComponentTypes.CUSTOM_NAME,
        DataComponentTypes.LORE
    )

    fun translateItemStack(player: UUID, itemStack: ItemStack): ItemStack {
        if (!itemStack.hasComponents()) return itemStack

        if (itemStack.dataComponents.has(DataComponentTypes.ITEM_NAME)) {
            val component = itemStack.dataComponents.get<BaseComponent>(DataComponentTypes.ITEM_NAME)
            itemStack.dataComponents.put(DataComponentTypes.ITEM_NAME, componentHelper.translateComponent(
                player, component
            ))
        }

        if (itemStack.dataComponents.has(DataComponentTypes.CUSTOM_NAME)) {
            val component = itemStack.dataComponents.get<BaseComponent>(DataComponentTypes.CUSTOM_NAME)
            itemStack.dataComponents.put(DataComponentTypes.CUSTOM_NAME, componentHelper.translateComponent(
                player, component
            ))
        }

        if (itemStack.dataComponents.has(DataComponentTypes.LORE)) {
            val component = itemStack.dataComponents.get<List<BaseComponent>>(DataComponentTypes.LORE)
            val translated = ArrayList<BaseComponent>()
            for ((index, baseComponent) in component.withIndex()) {
                val translatedComponent = componentHelper.translateComponent(
                    player, baseComponent
                )
                translated.add(translatedComponent)
            }
            itemStack.dataComponents.put(DataComponentTypes.LORE, translated)
        }
        return itemStack
    }

}