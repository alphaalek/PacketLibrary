package me.alek.packetlibrary.packet.type;

import me.alek.packetlibrary.utility.protocol.ProtocolRange;

public interface RangedPacketTypeEnum extends PacketTypeEnum {

    ProtocolRange getRange();
}
