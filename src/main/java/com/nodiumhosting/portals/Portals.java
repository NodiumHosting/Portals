package com.nodiumhosting.portals;

import com.mojang.logging.LogUtils;
import com.nodiumhosting.portals.command.PortalCommand;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

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

        PortalManager.loadPortals(event.getServer());
    }

    @Mod.EventBusSubscriber(modid = Portals.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModEventListener {
        @SubscribeEvent
        public static void registerClientCommands(RegisterClientCommandsEvent event) {
            PortalCommand.register(event.getDispatcher());
        }
    }
}
