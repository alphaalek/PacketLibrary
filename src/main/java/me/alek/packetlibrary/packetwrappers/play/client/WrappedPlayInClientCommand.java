package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInClientCommand extends WrappedPacket<WrappedPlayInClientCommand> {
    public WrappedPlayInClientCommand(Object rawPacket, PacketContainer<WrappedPlayInClientCommand> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
