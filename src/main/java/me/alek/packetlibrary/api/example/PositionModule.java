package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.wrappers.play.client.WrappedPlayInPosition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class PositionModule extends AsyncPacketAdapter<WrappedPlayInPosition> {

    public PositionModule() {
        PacketLibrary.get().addListener(this, () -> PacketType.Play.Client.POSITION);
    }

    @Override
    public void onPacketReceive(Player player, PacketContainer<WrappedPlayInPosition> packet) {
        Bukkit.broadcastMessage("§6packet received! "
            + packet.getDoubles().read(0) + " " + packet.getDoubles().read(1) + " " + packet.getDoubles().read(2));
    }
}
