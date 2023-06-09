package me.alek.packetlibrary.wrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class WrappedPlayOutNamedSoundEffect extends WrappedPacket<WrappedPlayOutNamedSoundEffect> {

    public WrappedPlayOutNamedSoundEffect(Object rawPacket, PacketContainer<WrappedPlayOutNamedSoundEffect> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
