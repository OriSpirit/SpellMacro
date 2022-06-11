package com.spiritlight.spellmacro;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;
import java.util.*;

@Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION)
public class MainMod
{
    public static final String MODID = "spell";
    public static final String NAME = "SpellMacros";
    public static final String VERSION = "1.0";
    static final Map<Integer, SpellSequence> macroList = new HashMap<>(4);
    static final List<Boolean> cancel = new ArrayList<>(Arrays.asList(false));


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        try {
            ConfigSpirit.getConfig();
        } catch (IOException e) {
            try {
                ConfigSpirit.writeConfig();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        KeyBindings.register();
        ClientCommandHandler.instance.registerCommand(new CommandHandler());
        MinecraftForge.EVENT_BUS.register(new KeyBindings());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
