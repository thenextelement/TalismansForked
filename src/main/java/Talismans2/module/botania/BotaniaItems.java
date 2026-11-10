package Talismans2.module.botania;

import net.minecraft.item.Item;

import Talismans2.module.botania.items.ItemManaFlameTalisman;
import cpw.mods.fml.common.registry.GameRegistry;

public class BotaniaItems {

    public static Item FlameManaTalisman;

    public static void init() {
        FlameManaTalisman = new ItemManaFlameTalisman();
        GameRegistry.registerItem(FlameManaTalisman, "manaFlameTalisman");
    }

}
