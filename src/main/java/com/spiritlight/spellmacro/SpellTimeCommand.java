package com.spiritlight.spellmacro;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault @MethodsReturnNonnullByDefault
public class SpellTimeCommand extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "spelltime";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int delay;
        int spellID;
        if(args.length == 0) {
            AnnouncerSpirit.send("\n/spelltime <id> <duration>" +
                    "\nDuration must be longer than 5ms.");
            return;
        }
        try {
            spellID = Integer.parseInt(args[0])-1;
            if(spellID < 0 || spellID >= 4) {
                AnnouncerSpirit.send("Invalid spell ID.");
                return;
            }
        } catch (NumberFormatException exception) {
            AnnouncerSpirit.send("Invalid spell ID.");
            return;
        }
        if(args.length == 1) {
            try {
                AnnouncerSpirit.send("Current spell delay for ID " + (spellID+1) + " is " + MainMod.macroList.get(spellID).getCastDelay() + "ms");
            } catch (NullPointerException e) {
                AnnouncerSpirit.send("Invalid spell ID.");
            }
            return;
        }
        try {
            delay = Integer.parseInt(args[1]);
        } catch (NumberFormatException|IndexOutOfBoundsException outOfBoundsException) {
            AnnouncerSpirit.send("Invalid delay.");
            return;
        }
        try {
            MainMod.macroList.get(spellID).setCastDelay(delay);
            ConfigSpirit.writeConfig();
            AnnouncerSpirit.send("Successfully set ID " + (spellID+1) + "'s delay to " + delay + " ms.");
        } catch (NullPointerException e) {
            AnnouncerSpirit.send("This spell has not been defined yet. Please define it before setting a delay.");
        } catch (IllegalArgumentException e) {
            AnnouncerSpirit.send(e.getMessage());
        } catch (IOException e) {
            AnnouncerSpirit.send("An exception has occurred.");
            e.printStackTrace();
        }
    }
}
