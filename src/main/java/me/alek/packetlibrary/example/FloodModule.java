package me.alek.packetlibrary.example;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.UnparameterizedPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;

public class FloodModule extends UnparameterizedPacketAdapter {

    public FloodModule() {
        PluginTest.get().getPacketLibrary().addUnparameterizedListener(this, PacketType.getTypesAvailableFor(Protocol.getProtocol()));
    }

    @Override
    public void onPacketReceive(PacketContainer<WrappedPacket<?>> packetContainer) {
        Bukkit.getLogger().info(packetContainer.getPacket() + "");
    }

    public void onPacketSend(PacketContainer<WrappedPacket<?>> packetContainer) {

    }

}
