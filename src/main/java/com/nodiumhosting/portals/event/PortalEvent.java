package com.nodiumhosting.portals.event;

import com.nodiumhosting.portals.portal.Portal;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.DEDICATED_SERVER})
public class PortalEvent {
    @SubscribeEvent
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
        event.getEntityLiving().sendMessage(new TextComponent("test"), event.getEntityLiving().getUUID());
        if (!(event.getEntityLiving() instanceof Player)) return;
        Player player = (Player) event.getEntityLiving();

        player.sendMessage(new TextComponent("a"), player.getUUID());

        BlockPos pos = player.blockPosition();
        BlockState blockState = player.level.getBlockState(pos);
        if (!blockState.getCollisionShape(player.level, pos).isEmpty()) return;

        player.sendMessage(new TextComponent("b"), player.getUUID());

        if (!PortalManager.isInsidePortal(player)) return;

        player.sendMessage(new TextComponent("c"), player.getUUID());

        Portal portal = PortalManager.getPortal(player);
        if (portal == null) return;
        portal.performActions(player);
    }
}
