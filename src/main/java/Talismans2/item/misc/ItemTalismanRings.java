package Talismans2.item.misc;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;

import Talismans2.item.ItemTalismanBauble;
import baubles.api.BaubleType;

public class ItemTalismanRings extends ItemTalismanBauble {

    // Names For Rings Depending on Meta Data
    public static final String[] types = new String[] { "Blank", "Crafting", "Flame", "Invisible", "Light", "Mining",
        "Movement", "Water", "Witherless", };

    private IIcon[] textures;

    public ItemTalismanRings() {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName("Talisman Ring");

    }

    @Override
    // Tells Baubles What slot Rings Can Be Equiped To
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    // Registers Textures For All Rings
    public void registerIcons(IIconRegister iconRegister) {
        textures = new IIcon[types.length];

        for (int i = 0; i < types.length; ++i) {
            textures[i] = iconRegister.registerIcon("talismans2:" + "Ring");
        }
    }

    @Override
    // Adds Texture what match's meta data
    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= textures.length) {
            meta = 0;
        }

        return textures[meta];
    }

    @Override
    // gets Unlocalized Name depending on meta data
    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= types.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + types[meta];
    }

    // Adds Rings SubItems To Creative Tab
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int meta = 0; meta < types.length; ++meta) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        super.onWornTick(itemstack, player);
        switch (itemstack.getItemDamage()) {
            case 2 -> applyEffect(player, Potion.fireResistance, 1);
            case 3 -> applyEffect(player, Potion.invisibility, 1);
            case 4 -> applyEffect(player, Potion.nightVision, 1);
            case 5 -> applyEffect(player, Potion.digSpeed, 1);
            case 6 -> {
                applyEffect(player, Potion.moveSpeed, 1);
                applyEffect(player, Potion.jump, 1);
            }
            case 7 -> applyEffect(player, Potion.waterBreathing, 1);
            case 8 -> player.removePotionEffect(Potion.wither.id);
            default -> {}
        }
    }

    @Override
    // Removes Potion effect on Unequip
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        switch (itemstack.getItemDamage()) {
            case 2 -> removeEffect(player, Potion.fireResistance);
            case 3 -> removeEffect(player, Potion.invisibility);
            case 4 -> removeEffect(player, Potion.nightVision);
            case 5 -> removeEffect(player, Potion.digSpeed);
            case 6 -> {
                removeEffect(player, Potion.moveSpeed);
                removeEffect(player, Potion.jump);
            }
            case 7 -> removeEffect(player, Potion.waterBreathing);
            default -> {}
        }
    }
}
