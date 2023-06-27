package me.alek.packetlibrary.packetwrappers.login.client;

import com.mojang.authlib.GameProfile;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;

public class WrappedLoginInLoginStart extends WrappedPacket<WrappedLoginInLoginStart> {

    public WrappedLoginInLoginStart(Object rawPacket, PacketContainer<WrappedLoginInLoginStart> packetContainer) {
        super(rawPacket, packetContainer);
    }

    public GameProfile getProfile() {
        return getContainer().getObjects(GameProfile.class).readField(0);
    }
}
