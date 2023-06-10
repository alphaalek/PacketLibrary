package me.alek.packetlibrary.api.example;

import me.alek.packetlibrary.PacketLibrary;
import me.alek.packetlibrary.api.packet.container.PacketContainer;
import me.alek.packetlibrary.listener.AsyncPacketAdapter;
import me.alek.packetlibrary.packet.type.PacketBound;
import me.alek.packetlibrary.packet.type.PacketType;
import me.alek.packetlibrary.wrappers.WrappedPacket;
import me.alek.packetlibrary.wrappers.play.client.WrappedPlayInChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatModule extends AsyncPacketAdapter<WrappedPlayInChat> {

    public ChatModule (){
        PacketLibrary.get().addListener(this, () -> PacketType.Play.Client.CHAT);
    }

    @Override
    public void onPacketCancel(Player player, PacketContainer<WrappedPlayInChat> packetContainer, PacketBound bound) {
        Bukkit.broadcastMessage("cancelled!");
    }
}
