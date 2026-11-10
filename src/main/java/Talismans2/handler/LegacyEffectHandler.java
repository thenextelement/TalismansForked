package Talismans2.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 * Older versions applied effects lasting Integer.MAX_VALUE ticks, which are saved with the player and outlive the
 * talisman. Strip them on login; worn talismans reapply their effects on the next tick.
 */
public class LegacyEffectHandler {

    private static final int[] TALISMAN_POTIONS = { Potion.fireResistance.id, Potion.invisibility.id,
        Potion.nightVision.id, Potion.digSpeed.id, Potion.moveSpeed.id, Potion.jump.id, Potion.waterBreathing.id };

    @SubscribeEvent
    public void onLogin(PlayerLoggedInEvent event) {
        List<Integer> stale = new ArrayList<>();
        for (int id : TALISMAN_POTIONS) {
            PotionEffect effect = event.player.getActivePotionEffect(Potion.potionTypes[id]);
            // Negative amplifiers are other mods' markers (Botania uses -42)
            if (effect != null && effect.getIsAmbient()
                && effect.getAmplifier() >= 0
                && effect.getDuration() > 1_000_000) stale.add(id);
        }
        for (int id : stale) event.player.removePotionEffect(id);
    }
}
