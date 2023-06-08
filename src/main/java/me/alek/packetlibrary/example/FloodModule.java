package me.alek.packetlibrary.example;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.UnparameterizedPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.utility.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FloodModule extends UnparameterizedPacketAdapter {

    public FloodModule() {
        PluginTest.get().getPacketLibrary().addUnparameterizedListener(this, PacketType.getTypesAvailableFor(Protocol.getProtocol()));
    }

    @Override
    public void onPacketReceive(Player player, PacketContainer packetContainer) {
        Bukkit.getLogger().info(packetContainer.getPacket() + "");
    }

    public void onPacketSend(Player player, PacketContainer packetContainer) {

    }

}
