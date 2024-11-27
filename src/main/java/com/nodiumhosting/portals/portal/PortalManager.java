package com.nodiumhosting.portals.portal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nodiumhosting.portals.Portals;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PortalManager {
    private static List<Portal> portals = List.of();

    public static int loadPortals(Path path) {
        if (path == null) return -1;

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                Files.writeString(path, "[]");
            } catch (Exception e) {
                Portals.LOGGER.error("(Portals) Failed to create portals file: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            String json = Files.readString(path);
            Gson gson = new Gson();
            Type portalListType = new TypeToken<List<Portal>>() {}.getType();
            List<Portal> savedPortals = gson.fromJson(json, portalListType);

            if (savedPortals != null) {
                portals = savedPortals;
            }

            return 1;
        } catch (Exception e) {
            Portals.LOGGER.error("(Portals) Failed to load portals: " + e.getMessage());
            e.printStackTrace();

            return -1;
        }
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
