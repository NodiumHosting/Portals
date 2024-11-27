package com.nodiumhosting.portals.portal;

import com.nodiumhosting.portals.Portals;
import com.nodiumhosting.portals.portal.actions.CommandAction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PortalManager {
    private static List<Portal> portals = List.of();

    public static void loadPortals(MinecraftServer server) {
        PortalSavedData savedData = PortalSavedData.getData(server);
        List<Portal> savedPortals = savedData.getPortals();
        if (savedPortals != null) {
            portals = savedPortals;
        }

        // Test portals
        List<Portal> testPortals = List.of(
                new Portal(new BlockPos(20, 120, 20), new BlockPos(30, 130, 20), List.of("minecraft:water"), List.of(new CommandAction("say Hello, world!")))
        );
        portals = testPortals;
        savedData.setPortals(testPortals);
        Portals.LOGGER.info("(Portals) Loaded portals");
    }

    public static List<Portal> getPortals() {
        return portals;
    }

    public static boolean isInsidePortal(Player player) {
        ResourceLocation registryName = player.level.getBlockState(player.blockPosition()).getBlock().getRegistryName();
        if (registryName == null) return false;
        String blockName = registryName.toString();

        for (Portal portal : portals) {
            if (portal.isInside(player.blockPosition()) && portal.isBlock(blockName)) {
                return true;
            }
        }
        return false;
    }

    public static Portal getPortal(Player player) {
        // Return the first portal that the player is inside
        // Note: The player might be inside multiple portals at once if they are overlapping which this method does not account for
        ResourceLocation registryName = player.level.getBlockState(player.blockPosition()).getBlock().getRegistryName();
        if (registryName == null) return null;
        String blockName = registryName.toString();

        for (Portal portal : portals) {
            if (portal.isInside(player.blockPosition()) && portal.isBlock(blockName)) {
                return portal;
            }
        }
        return null;
    }
}
