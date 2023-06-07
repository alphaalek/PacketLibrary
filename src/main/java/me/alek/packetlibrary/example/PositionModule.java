package me.alek.packetlibrary.example;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.wrappers.play.client.WrappedPlayInPosition;
import org.bukkit.Bukkit;

public class PositionModule extends AsyncPacketAdapter<WrappedPlayInPosition> {

    public PositionModule() {
        PluginTest.get().getPacketLibrary().addListener( this, PacketType.Play.Client.POSITION);
    }

    @Override
    public void onPacketReceive(PacketContainer<WrappedPlayInPosition> packet) {
        Bukkit.broadcastMessage("§6packet received! " + packet.getPacket() + " " + packet.getType());
    }
}
