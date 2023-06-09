package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.PluginTest;
import me.alek.packetlibrary.api.event.impl.PacketStatusReceiveEvent;
import me.alek.packetlibrary.api.event.impl.PacketStatusSendEvent;
import me.alek.packetlibrary.packet.type.PacketType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PacketStatusListener implements Listener {

    public PacketStatusListener() {
        PacketLibrary.get().getEventManager().addListener(this);
    }

    @EventHandler
    public void onSend(PacketStatusSendEvent event) {
        Bukkit.broadcastMessage("Packet fra" + event.getPlayer() + ": " + event.getPacket().getHandle());
    }

    @EventHandler
    public void onReceive(PacketStatusReceiveEvent event) {
        if (event.getPacket().getType() == PacketType.Status.Client.STATUS_REQUEST) {
            Bukkit.broadcastMessage(event.getPlayer() + " modtog en status request packet!");
        }
    }
}
