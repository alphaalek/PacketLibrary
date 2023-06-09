package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutSpawnEntityWeather extends WrappedPacket<WrappedPlayOutSpawnEntityWeather> {

    public WrappedPlayOutSpawnEntityWeather(Object rawPacket, PacketContainer<WrappedPlayOutSpawnEntityWeather> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
