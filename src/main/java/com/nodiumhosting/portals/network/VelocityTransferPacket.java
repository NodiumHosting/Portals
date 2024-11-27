package com.nodiumhosting.portals.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VelocityTransferPacket {
    private String server;

    public VelocityTransferPacket(String server) {
        this.server = server;
    }

    public static void encode(VelocityTransferPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.server);
    }

    public static VelocityTransferPacket decode(FriendlyByteBuf buf) {
        String server = buf.readUtf();
        return new VelocityTransferPacket(server);
    }

    public static void handle(VelocityTransferPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            // Do stuff
        });
        ctx.get().setPacketHandled(true);
    }
}
