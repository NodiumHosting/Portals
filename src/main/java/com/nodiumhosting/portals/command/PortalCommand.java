package com.nodiumhosting.portals.command;

import com.google.gson.Gson;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.nodiumhosting.portals.Portals;
import com.nodiumhosting.portals.events.PortalEvent;
import com.nodiumhosting.portals.network.PacketHandler;
import com.nodiumhosting.portals.network.VelocityTransferPacket;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.network.PacketDistributor;

import java.nio.file.Path;
import java.util.function.Supplier;

public class PortalCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("portals")
                        .then(Commands.literal("reload")
                                .requires(source -> source.hasPermission(2))
                                .executes(ctx -> {
                                    Path path = ctx.getSource().getServer().getWorldPath(LevelResource.ROOT).normalize().toAbsolutePath().resolve("portals.json");
                                    int success = PortalManager.loadPortals(path);
                                    if (success == 1) {
                                        ctx.getSource().sendSuccess(new TextComponent("Portals reloaded!"), true);
                                    } else {
                                        ctx.getSource().sendFailure(new TextComponent("Failed to reload portals!"));
                                    }
                                    return success;
                                })
                        )
                        .then(Commands.literal("debug")
                                .requires(source -> source.hasPermission(2))
                                .executes(ctx -> {
                                    Gson gson = new Gson();
                                    Portals.LOGGER.info("Portal debug: " + gson.toJson(PortalManager.getPortals()));
                                    return 1;
                                })
                        )
                        .then(Commands.literal("packet")
                                .requires(source -> source.hasPermission(2))
                                .executes(ctx -> {
                                    try {
                                        ServerPlayer player = ctx.getSource().getPlayerOrException();
                                        Supplier<ServerPlayer> supplier = () -> player;
                                        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(supplier), new VelocityTransferPacket("test"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return 1;
                                })
                        )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        return Command.SINGLE_SUCCESS;
    }
}
