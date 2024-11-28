package com.nodiumhosting.portals.events;

import com.nodiumhosting.portals.portal.Portal;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber({Dist.DEDICATED_SERVER})
public class PortalEvent {
    private static final Map<UUID, Long> lastAction = new java.util.HashMap<>();
    private static final long actionCooldown = 1000;

    @SubscribeEvent
    public static void onEvent(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (!(livingEntity instanceof Player)) return;
        Player player = (Player) livingEntity;

        UUID uuid = player.getUUID();
        if (lastAction.containsKey(uuid) && System.currentTimeMillis() - lastAction.get(uuid) < actionCooldown) return;

        BlockPos pos = player.blockPosition();
        BlockState blockState = player.level.getBlockState(pos);
        if (!blockState.getCollisionShape(player.level, pos).isEmpty()) return;
        if (!PortalManager.isInsidePortal(player)) return;

        Portal portal = PortalManager.getPortal(player);
        if (portal == null) return;

        lastAction.put(uuid, System.currentTimeMillis());
        portal.performActions(player);
    }
}
