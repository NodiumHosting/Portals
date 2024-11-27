package com.nodiumhosting.portals.portal;

import com.google.gson.Gson;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortalSavedData extends SavedData {
    private List<Portal> portals;

    public List<Portal> getPortals() {
        return this.portals;
    }

    public void setPortals(List<Portal> portals) {
        this.portals = portals;
        this.setDirty();
    }

    public static PortalSavedData create() {
        return new PortalSavedData();
    }

    public static PortalSavedData load(CompoundTag tag) {
        PortalSavedData data = PortalSavedData.create();
//        int testInt = tag.getInt("test");
//        data.test = testInt;
        //deserialize json from byte array and set to portals
        Gson gson = new Gson();
        String json = new String(tag.getByteArray("portals"));
        data.portals = gson.fromJson(json, List.class);
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
//        tag.putInt("test", this.test);
        //serialize portals to json and save to tag as byte array
        Gson gson = new Gson();
        String json = gson.toJson(this.portals);
        tag.putByteArray("portals", json.getBytes());
        return tag;
    }

    public static PortalSavedData getData(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(PortalSavedData::load, PortalSavedData::create, "portals");
    }
}
