package com.nodiumhosting.portals.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class PortalAction {
    public static PortalAction newCommandAction(String command) {
        PortalAction action = new PortalAction();
        action.type = ActionType.COMMAND;
        action.command = command;
        return action;
    }

    public static PortalAction newTeleportAction(BlockPos destination) {
        PortalAction action = new PortalAction();
        action.type = ActionType.TELEPORT;
        action.destination = destination;
        return action;
    }
    
    public enum ActionType {
        COMMAND("command"),
        TELEPORT("teleport");

        private final String name;

        ActionType(String name) {
            this.name = name;
        }
    }

    private ActionType type;
    private String command;
    private BlockPos destination;

    public void onPortalEnter(Player player) {
        switch (type) {
            case COMMAND:
                player.getServer().getCommands().performCommand(player.createCommandSourceStack().withPermission(4), command);
                break;
            case TELEPORT:
                player.teleportTo(destination.getX(), destination.getY(), destination.getZ());
                break;
        }
    }
}
