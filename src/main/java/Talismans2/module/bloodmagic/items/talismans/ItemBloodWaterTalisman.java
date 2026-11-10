package Talismans2.module.bloodmagic.items.talismans;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import Talismans2.Talismans2;
import Talismans2.item.ItemTalismanBauble;
import Talismans2.module.bloodmagic.items.ItemBloodTalisman;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import baubles.api.BaubleType;

public class ItemBloodWaterTalisman extends ItemBloodTalisman {

    public ItemBloodWaterTalisman() {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName("Blood Water Talisman");
        this.setCreativeTab(Talismans2.tabsTalismans);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("talismans2:Water_Blood");
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack arg0, EntityLivingBase arg1) {

    }

    @Override
    public void onUnequipped(ItemStack arg0, EntityLivingBase player) {
        ItemTalismanBauble.removeEffect(player, Potion.waterBreathing);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (ItemTalismanBauble.needsEffect(player, Potion.waterBreathing, 1)
            && SoulNetworkHandler.syphonFromNetwork(stack, 15) > 0) {
            ItemTalismanBauble.applyEffect(player, Potion.waterBreathing, 1);
        }
    }

}
