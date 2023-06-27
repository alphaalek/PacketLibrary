package me.alek.packetlibrary.packetwrappers.play.server;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedPlayOutSelectAdvancementTab extends WrappedPacket<WrappedPlayOutSelectAdvancementTab> {

    public WrappedPlayOutSelectAdvancementTab(Object rawPacket, PacketContainer<WrappedPlayOutSelectAdvancementTab> packetContainer) {
        super(rawPacket, packetContainer);
    }
}
