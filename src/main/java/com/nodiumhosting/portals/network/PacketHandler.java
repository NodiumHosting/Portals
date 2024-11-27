package com.nodiumhosting.portals.network;

import com.nodiumhosting.portals.Portals;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Portals.MODID, "velocity_transfer"),
            () -> PROTOCOL_VERSION,
            NetworkRegistry.ACCEPTVANILLA::equals,
            NetworkRegistry.ACCEPTVANILLA::equals
    );

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, VelocityTransferPacket.class, VelocityTransferPacket::encode, VelocityTransferPacket::decode, VelocityTransferPacket::handle);
    }
}
