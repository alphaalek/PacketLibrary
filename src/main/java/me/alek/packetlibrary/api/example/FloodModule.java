package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.FuzzyPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.utility.protocol.Protocol;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FloodModule extends FuzzyPacketAdapter {

    public FloodModule() {
        PacketLibrary.get().addFuzzyListeners(this, () -> PacketType.getTypesAvailableFor(Protocol.getProtocol()));
    }

    @Override
    public void onPacketReceive(Player player, PacketContainer packetContainer) {
        Bukkit.getLogger().info("read: " + packetContainer.getType() + "");
    }

    @Override
    public void onPacketSend(Player player, PacketContainer packetContainer) {
        Bukkit.getLogger().info("write: " + packetContainer.getType() + "");
    }

}
