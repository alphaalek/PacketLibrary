package me.alek.packetlibrary.example;

import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.listener.PacketDuplexAdapter;
import me.alek.packetlibrary.packet.PacketType;

public class PositionModule extends PacketDuplexAdapter {

    public PositionModule() {
        PluginTest.get().getPacketLibrary().addListener(PacketType.Status.Client.STATUS_REQUEST, this);
    }
}
