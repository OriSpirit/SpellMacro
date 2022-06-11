package com.spiritlight.spellmacro;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.Arrays;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandHandler extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "spell";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int spellID;
        if(args.length == 0) {
            AnnouncerSpirit.send("\n/spell <id> <sequence>" +
                    "\nSequence has to be separated from each other." +
                    "\nValid input: L/R for left/right click.");
            return;
        }
        try {
            // only doing this for sake of user-friendliness
            spellID = Integer.parseInt(args[0]) - 1;
            if(spellID < 0 || spellID >= 4) {
                AnnouncerSpirit.send("Spell ID must be in between 1 and 4.");
                return;
            }
        } catch (NumberFormatException ignored) {
            AnnouncerSpirit.send("Invalid index.");
            return;
        }
        if(args.length == 1) {
            try {
                AnnouncerSpirit.send("Current spell sequence for this ID is " + MainMod.macroList.get(spellID).getRawSequence());
            } catch (NullPointerException e) {
                AnnouncerSpirit.send("This sequence has not been defined a value yet.");
            }
            return;
        }
        String[] sequenceList = Arrays.copyOfRange(args, 1, args.length);
        String sequence = String.join(" ", sequenceList);
        MainMod.macroList.put(spellID, new SpellSequence(sequence));
        AnnouncerSpirit.send("Added sequence " + MainMod.macroList.get(spellID).getRawSequence() + " to ID " + (spellID+1));
        try {
            ConfigSpirit.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
