package com.nodiumhosting.portals.portal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class Portal {
    public BlockPos pos1;
    public BlockPos pos2;
    public List<String> portalBlocks;
    public List<PortalAction> actions;

    public Portal(BlockPos pos1, BlockPos pos2, List<String> portalBlocks, List<PortalAction> actions) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.portalBlocks = portalBlocks;
        this.actions = actions;
    }

    public boolean isInside(BlockPos pos) {
        boolean insideX = pos.getX() >= Math.min(pos1.getX(), pos2.getX()) && pos.getX() <= Math.max(pos1.getX(), pos2.getX());
        boolean insideY = pos.getY() >= Math.min(pos1.getY(), pos2.getY()) && pos.getY() <= Math.max(pos1.getY(), pos2.getY());
        boolean insideZ = pos.getZ() >= Math.min(pos1.getZ(), pos2.getZ()) && pos.getZ() <= Math.max(pos1.getZ(), pos2.getZ());

        return insideX && insideY && insideZ;
    }

    public boolean isBlock(String block) {
        return portalBlocks.stream().anyMatch(block::equals);
    }

    public void performActions(Player player) {
        for (PortalAction action : actions) {
            action.onPortalEnter(player);
        }
    }
}
