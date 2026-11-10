package Talismans2.item;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import Talismans2.Talismans2;
import Talismans2.lib.Modinfo;
import Talismans2.util.Color;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Gigabit101
 */

public class ItemTalismanBauble extends Item implements IBauble {

    public ItemTalismanBauble() {
        super();
        this.setCreativeTab(Talismans2.tabsTalismans);
        this.setMaxStackSize(1);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Modinfo.ID.toLowerCase() + ":" + getUnlocalizedName());
    }

    @Override
    public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
        return true;
    }

    // Effects are short and refreshed while worn, so nothing outlives the talisman
    // (logout, death, a bauble removed without onUnequipped).
    public static final int EFFECT_DURATION = 400;
    private static final int EFFECT_REFRESH = 300;

    public static boolean needsEffect(EntityLivingBase player, Potion potion, int amplifier) {
        if (player.worldObj.isRemote) return false;
        PotionEffect active = player.getActivePotionEffect(potion);
        return active == null || (active.getDuration() < EFFECT_REFRESH && active.getAmplifier() <= amplifier);
    }

    public static void applyEffect(EntityLivingBase player, Potion potion, int amplifier) {
        if (needsEffect(player, potion, amplifier)) {
            player.addPotionEffect(new PotionEffect(potion.id, EFFECT_DURATION, amplifier, true));
        }
    }

    // Leaves longer effects from other sources alone
    public static void removeEffect(EntityLivingBase player, Potion potion) {
        if (player.worldObj.isRemote) return;
        PotionEffect active = player.getActivePotionEffect(potion);
        if (active != null && active.getDuration() <= EFFECT_DURATION) player.removePotionEffect(potion.id);
    }

    // Loot chests used to generate size-0 stacks, which vanish when dropped
    protected static void repairStack(ItemStack stack) {
        if (stack != null && stack.stackSize <= 0) stack.stackSize = 1;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held) {
        repairStack(stack);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        repairStack(stack);
    }

    @Override
    public void onPlayerLoad(ItemStack arg0, EntityLivingBase arg1) {
        onEquippedOrLoadedIntoWorld(arg0, arg1);
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return null;
    }

    @Override
    public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
        onEquippedOrLoadedIntoWorld(arg0, arg1);
    }

    @Override
    public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {}

    public void onEquippedOrLoadedIntoWorld(ItemStack arg0, EntityLivingBase arg1) {
        // NO-OP
    }

    public boolean isFull3D() {
        return true;
    }

    @Override
    // Equips Talismans When Right Clicked In Players Hand
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
        for (int i = 0; i < baubles.getSizeInventory(); i++) {
            if (baubles.isItemValidForSlot(i, par1ItemStack)) {
                ItemStack stackInSlot = baubles.getStackInSlot(i);

                if (stackInSlot == null
                    || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, par3EntityPlayer)) {
                    if (!par2World.isRemote) {
                        baubles.setInventorySlotContents(i, par1ItemStack.copy());

                        if (!par3EntityPlayer.capabilities.isCreativeMode) par3EntityPlayer.inventory
                            .setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                    }

                    onEquipped(par1ItemStack, par3EntityPlayer);

                    if (stackInSlot != null) {
                        ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, par3EntityPlayer);
                        return stackInSlot.copy();
                    }
                    break;
                }
            }
        }

        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack iS, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        if (GuiScreen.isShiftKeyDown()) {
            par3List.add(StatCollector.translateToLocal("toolTip1" + iS.getUnlocalizedName()));

        } else {

            par3List.add(Color.DARK_PURPLE + StatCollector.translateToLocal(Modinfo.ID + "toolTipShift"));

        }
    }

}
