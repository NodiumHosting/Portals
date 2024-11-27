package com.nodiumhosting.portals.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class PortalCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("portal")
                        .executes(ctx -> {
                            ctx.getSource().sendSuccess(new TextComponent("Success"), true);
                            return 1;
                        })
        );
    }

    private static int execute(CommandContext<CommandSourceStack> command) {
        return Command.SINGLE_SUCCESS;
    }
}
