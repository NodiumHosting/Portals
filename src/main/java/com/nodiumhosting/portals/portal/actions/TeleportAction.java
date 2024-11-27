package com.nodiumhosting.portals.portal.actions;

import net.minecraft.core.Position;
import net.minecraft.world.entity.player.Player;

public class TeleportAction extends PortalAction {
    private Position destination;

    public TeleportAction(Position destination) {
        this.destination = destination;
    }

    @Override
    public void onPortalEnter(Player player) {
        player.teleportTo(destination.x(), destination.y(), destination.z());
    }
}
