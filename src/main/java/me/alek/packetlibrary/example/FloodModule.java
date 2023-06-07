package me.alek.packetlibrary.example;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.PacketType;
import me.alek.packetlibrary.utils.protocol.Protocol;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class FloodModule extends AsyncPacketAdapter<WrappedPacket> {

    public FloodModule() {
        PluginTest.get().getPacketLibrary().addListener(this, PacketType.getTypesAvailableFor(Protocol.getProtocol()));
    }
}
