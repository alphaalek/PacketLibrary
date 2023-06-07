package me.alek.packetlibrary.listener;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class AsyncPacketAdapter<WP extends WrappedPacket> {

    public void onPacketReceive(PacketContainer<WP> packetContainer) {

    }

    public void onPacketSend(PacketContainer<WP> packetContainer) {

    }
}
