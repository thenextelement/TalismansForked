package Talismans2.item.talismans;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import Talismans2.Talismans2;
import Talismans2.item.ItemTalismanBauble;
import baubles.api.BaubleType;

public class ItemMovementTalisman extends ItemTalismanBauble {

    public ItemMovementTalisman() {
        super();
        this.setUnlocalizedName("Movement Talisman");
        this.setCreativeTab(Talismans2.tabsTalismans);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("talismans2:Movement");
    }

    @Override
    // Returns Potion Effect on Tick
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        super.onWornTick(itemstack, player);
        applyEffect(player, Potion.moveSpeed, 1);
        applyEffect(player, Potion.jump, 1);
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack arg0, EntityLivingBase arg1) {

    }

    // Removes Potion effect on Unequip
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        removeEffect(player, Potion.moveSpeed);
        removeEffect(player, Potion.jump);
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.AMULET;
    }

}
