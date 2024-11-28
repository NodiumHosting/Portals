package com.nodiumhosting.portals.portal;

import com.nodiumhosting.portals.network.PacketHandler;
import com.nodiumhosting.portals.network.VelocityTransferPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class PortalAction {
    public static PortalAction newCommandAction(String command) {
        PortalAction action = new PortalAction();
        action.type = ActionType.COMMAND;
        action.command = command;
        return action;
    }

    public static PortalAction newTeleportAction(String x, String y, String z) {
        PortalAction action = new PortalAction();
        action.type = ActionType.TELEPORT;
        action.x = x;
        action.y = y;
        action.z = z;
        return action;
    }

    public static PortalAction newTransferAction(String server, String x, String y, String z) {
        PortalAction action = new PortalAction();
        action.type = ActionType.TRANSFER;
        action.server = server;
        action.x = x;
        action.y = y;
        action.z = z;
        return action;
    }
    
    public enum ActionType {
        COMMAND("command"),
        TELEPORT("teleport"),
        TRANSFER("transfer");

        private final String name;

        ActionType(String name) {
            this.name = name;
        }
    }

    private ActionType type;
    private String command;
    private String x;
    private String y;
    private String z;
    private String server;

    private Vec3 destination() {
        return new Vec3(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
    }

    public void onPortalEnter(Player player) {
        switch (type) {
            case COMMAND:
                player.getServer().getCommands().performCommand(player.createCommandSourceStack().withPermission(4), command);
                break;
            case TELEPORT:
                player.teleportTo(destination().x(), destination().y(), destination().z());
                break;
            case TRANSFER:
                player.teleportTo(destination().x(), destination().y(), destination().z());
                PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new VelocityTransferPacket(player.getStringUUID(), server));
                break;
        }
    }
}
