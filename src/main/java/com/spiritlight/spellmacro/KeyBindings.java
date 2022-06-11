package com.spiritlight.spellmacro;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;


public class KeyBindings
{
    public static final KeyBinding[] keyBindings = new KeyBinding[5];
    private static boolean alreadyCreated;

    public static void register() {
        if (KeyBindings.alreadyCreated) {
            return;
        }
        KeyBindings.alreadyCreated = true;
        for(KeyBinding k : keyBindings)
            ClientRegistry.registerKeyBinding(k);
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void onEvent(final InputEvent.KeyInputEvent e) {
        if(keyBindings[0].isPressed()) {
            SpellCast.cast(MainMod.macroList.get(0));
            return;
        }
        if(keyBindings[1].isPressed()) {
            SpellCast.cast(MainMod.macroList.get(1));
            return;
        }
        if(keyBindings[2].isPressed()) {
            SpellCast.cast(MainMod.macroList.get(2));
            return;
        }
        if(keyBindings[3].isPressed()) {
            SpellCast.cast(MainMod.macroList.get(3));
            return;
        }
        if(keyBindings[4].isPressed()) {
            SpellCast.cast(MainMod.macroList.get(0));
            return;
        }
    }

    static {
        keyBindings[0] = new KeyBinding("Custom Spell 1", Keyboard.KEY_Z, "MacroMod");
        keyBindings[1] = new KeyBinding("Custom Spell 2", Keyboard.KEY_X, "MacroMod");
        keyBindings[2] = new KeyBinding("Custom Spell 3", Keyboard.KEY_C, "MacroMod");
        keyBindings[3] = new KeyBinding("Custom Spell 4", Keyboard.KEY_V, "MacroMod");
        keyBindings[4] = new KeyBinding("Cancel Spell", Keyboard.KEY_B, "MacroMod");
        KeyBindings.alreadyCreated = false;
    }
}
