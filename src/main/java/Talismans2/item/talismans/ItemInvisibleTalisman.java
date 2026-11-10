package Talismans2.item.talismans;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import Talismans2.Talismans2;
import Talismans2.item.ItemTalismanBauble;
import baubles.api.BaubleType;

public class ItemInvisibleTalisman extends ItemTalismanBauble {

    public ItemInvisibleTalisman() {
        super();
        this.setCreativeTab(Talismans2.tabsTalismans);
        this.setUnlocalizedName("Invisible Talisman");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("talismans2:Invisible");
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        super.onWornTick(itemstack, player);
        applyEffect(player, Potion.invisibility, 1);
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack arg0, EntityLivingBase arg1) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        removeEffect(player, Potion.invisibility);
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

}
