package com.nodiumhosting.portals.portal;

import com.nodiumhosting.portals.portal.actions.PortalAction;
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
        boolean insideX = pos.getX() >= pos1.getX() && pos.getX() <= pos2.getX();
        boolean insideY = pos.getY() >= pos1.getY() && pos.getY() <= pos2.getY();
        boolean insideZ = pos.getZ() >= pos1.getZ() && pos.getZ() <= pos2.getZ();

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
