package com.nodiumhosting.portals.portal.actions;

import com.mojang.brigadier.CommandDispatcher;
import com.nodiumhosting.portals.Portals;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

public class CommandAction extends PortalAction {
    private String command;

    public CommandAction(String command) {
        this.command = command;
    }

    public void onPortalEnter(Player player) {
        CommandSourceStack sourceStack = player.createCommandSourceStack();
        MinecraftServer server = player.getServer();
        if (server == null) return;
        Commands serverCommands = server.getCommands();
        CommandDispatcher<CommandSourceStack> dispatcher = serverCommands.getDispatcher();
        try {
            dispatcher.execute(command, sourceStack);
        } catch (Exception e) {
            Portals.LOGGER.error("(Portals) Failed to execute command: " + command);
        }
    }
}
