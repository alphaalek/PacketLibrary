package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInChat extends WrappedPacket<WrappedPlayInChat> {

    public WrappedPlayInChat(Object rawPacket, PacketContainer<WrappedPlayInChat> packetContainer) {
        super(rawPacket, packetContainer);
    }

    public String getMessage() {
        return getContainer().getStrings().readField(0);
    }
}
