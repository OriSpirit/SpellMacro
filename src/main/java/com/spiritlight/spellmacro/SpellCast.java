package com.spiritlight.spellmacro;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpellCast {
    private static final CPacketAnimation leftClick = new CPacketAnimation(EnumHand.MAIN_HAND);
    private static final CPacketPlayerTryUseItem rightClick = new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND);
    public static boolean isOccupied = false;

    public static void cast(final SpellSequence sequence) {
        if(isOccupied) {
            AnnouncerSpirit.send("This spell sequence hasn't finished.");
        }
        isOccupied = true;
        // We care about important stuffs later
        if(sequence == null || sequence.getSpell() == null) {
            AnnouncerSpirit.send("Cannot cast this spell: It has to be defined before casting.");
            isOccupied = false;
            return;
        }
        CompletableFuture.runAsync(() -> {
            List<Integer> spell = sequence.getSpell();
            for (Integer integer : spell) {
                if(MainMod.cancel.get(0)) {
                    MainMod.cancel.set(0, false);
                    AnnouncerSpirit.send("Cancelled current spell cast sequence.");
                    return;
                }
                Minecraft.getMinecraft().getConnection().sendPacket(integer == 0 ? leftClick : rightClick);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }).exceptionally(e -> {
            AnnouncerSpirit.sendException((Exception) e);
            isOccupied = false;
            return null;
        }).thenAccept(i -> isOccupied = false);
    }
}
