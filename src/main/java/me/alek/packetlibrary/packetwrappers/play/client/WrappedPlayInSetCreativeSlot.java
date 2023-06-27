package me.alek.packetlibrary.packetwrappers.play.client;

import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.packetwrappers.WrappedPacket;
import org.bukkit.inventory.ItemStack;

public class WrappedPlayInSetCreativeSlot extends WrappedPacket<WrappedPlayInSetCreativeSlot> {

    public WrappedPlayInSetCreativeSlot(Object rawPacket, PacketContainer<WrappedPlayInSetCreativeSlot> packetContainer) {
        super(rawPacket, packetContainer);
    }

    public ItemStack getItem() {
        return getContainer().getItems().readField(0);
    }

    public int getSlot() {
        return getContainer().getInts().readField(0);
    }
}
