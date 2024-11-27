package com.nodiumhosting.portals.portal;

import com.nodiumhosting.portals.events.PortalEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Portal {
    public String x1;
    public String y1;
    public String z1;
    public String x2;
    public String y2;
    public String z2;
    public List<String> portalBlocks;
    public List<PortalAction> actions;

    public Portal(String x1, String y1, String z1, String x2, String y2, String z2, List<String> portalBlocks, List<PortalAction> actions) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.portalBlocks = portalBlocks;
        this.actions = actions;
    }

    public BlockPos pos1() {
        return new BlockPos(Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(z1));
    }

    public BlockPos pos2() {
        return new BlockPos(Integer.parseInt(x2), Integer.parseInt(y2), Integer.parseInt(z2));
    }

    public boolean isInside(BlockPos pos) {
//        boolean insideX = pos.getX() >= Math.min(pos1.x(), pos2.x()) && pos.getX() <= Math.max(pos1.x(), pos2.x());
//        boolean insideY = pos.getY() >= Math.min(pos1.y(), pos2.y()) && pos.getY() <= Math.max(pos1.y(), pos2.y());
//        boolean insideZ = pos.getZ() >= Math.min(pos1.z(), pos2.z()) && pos.getZ() <= Math.max(pos1.z(), pos2.z());
        boolean insideX = pos.getX() >= Math.min(pos1().getX(), pos2().getX()) && pos.getX() <= Math.max(pos1().getX(), pos2().getX());
        boolean insideY = pos.getY() >= Math.min(pos1().getY(), pos2().getY()) && pos.getY() <= Math.max(pos1().getY(), pos2().getY());
        boolean insideZ = pos.getZ() >= Math.min(pos1().getZ(), pos2().getZ()) && pos.getZ() <= Math.max(pos1().getZ(), pos2().getZ());

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
