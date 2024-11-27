package com.nodiumhosting.portals;

import com.mojang.logging.LogUtils;
import com.nodiumhosting.portals.command.PortalCommand;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.nio.file.Path;

@Mod("portals")
public class Portals {
    public static final String MODID = "portals";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Portals() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("(Portals) Loading portals...");

        Path path = event.getServer().getWorldPath(LevelResource.ROOT).normalize().toAbsolutePath().resolve("portals.json");
        PortalManager.loadPortals(path);
    }

    @Mod.EventBusSubscriber(modid = Portals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
    public static class ModEventListener {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent event) {
            PortalCommand.register(event.getDispatcher());
        }
    }
}
