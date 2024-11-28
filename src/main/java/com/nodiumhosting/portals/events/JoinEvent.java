package com.nodiumhosting.portals.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//this is to prevent players from spawning inside the portal
@Mod.EventBusSubscriber({Dist.DEDICATED_SERVER})
public class JoinEvent {
    private static final Vec3 lobbySpawn = new Vec3(-126, 70, -106);

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        player.teleportTo(player.getLevel(), lobbySpawn.x(), lobbySpawn.y(), lobbySpawn.z(), 0, 0);
    }
}