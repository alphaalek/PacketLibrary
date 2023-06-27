package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayInTabComplete extends WrappedPacket<WrappedPlayInTabComplete> {

    public WrappedPlayInTabComplete(Object rawPacket, PacketContainer<WrappedPlayInTabComplete> packetContainer) {
        super(rawPacket, packetContainer);
    }

    public String getMessage() {
        return getContainer().getStrings().readField(0);
    }
}
