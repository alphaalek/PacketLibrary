package me.alek.packetlibrary.packet;

import me.alek.packetlibrary.utils.protocol.ProtocolRange;

public interface RangedPacketTypeEnum extends PacketTypeEnum {

    ProtocolRange getRange();
}
