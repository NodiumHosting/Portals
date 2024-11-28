package com.nodiumhosting.portals.network;

import com.google.gson.Gson;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VelocityTransferPacket {
    public String player;
    public String server;

    public VelocityTransferPacket(String player, String server) {
        this.player = player;
        this.server = server;
    }

    public static void encode(VelocityTransferPacket packet, FriendlyByteBuf buf) {
        Gson gson = new Gson();
        String json = gson.toJson(packet);
        buf.writeUtf(json);
    }

    public static VelocityTransferPacket decode(FriendlyByteBuf buf) {
        String json = buf.readUtf();
        Gson gson = new Gson();
        VelocityTransferPacket packet = gson.fromJson(json, VelocityTransferPacket.class);
        return packet;
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
