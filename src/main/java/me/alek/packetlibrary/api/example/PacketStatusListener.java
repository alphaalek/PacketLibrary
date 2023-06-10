package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.event.Listener;
import me.alek.packetlibrary.api.event.impl.packet.PacketPlayReceiveEvent;
import me.alek.packetlibrary.api.event.impl.packet.PacketStatusReceiveEvent;
import me.alek.packetlibrary.api.event.impl.packet.PacketStatusSendEvent;
import me.alek.packetlibrary.packet.type.PacketType;
import org.bukkit.Bukkit;


public class PacketStatusListener {

    public PacketStatusListener() {
        PacketLibrary.get().getEventManager().addListener(this);
    }

    @Listener
    public void onSend(PacketStatusSendEvent event) {
        Bukkit.broadcastMessage("Packet til " + event.getPlayer() + ": " + event.getPacket().getHandle());
    }

    @Listener
    public void onReceive(PacketStatusReceiveEvent event) {
        Bukkit.broadcastMessage("packet fra " + event.getPlayer() + ": " + event.getPacket().getHandle());
        if (event.getPacket().getType() == PacketType.Status.Client.STATUS_REQUEST) {
            Bukkit.broadcastMessage("modtog packet fra " + event.getPlayer());
        }
    }

    @Listener
    public void onChat(PacketPlayReceiveEvent event) {
        if (event.getPacket().getType() == PacketType.Play.Client.CHAT) {
            event.setCancelled(true);
        }
    }
}
