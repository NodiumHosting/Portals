package com.nodiumhosting.portals.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.nodiumhosting.portals.portal.PortalManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;

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
        );
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        return Command.SINGLE_SUCCESS;
    }
}
