package me.alek.packetlibrary.listener;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.wrappers.WrappedPacket;

public class UnparameterizedPacketAdapter extends AsyncPacketAdapter<WrappedPacket<?>> {

    public void onPacketReceive(PacketContainer<WrappedPacket<?>> packetContainer) {

    }

    public void onPacketSend(PacketContainer<WrappedPacket<?>> packetContainer) {

    }
}
