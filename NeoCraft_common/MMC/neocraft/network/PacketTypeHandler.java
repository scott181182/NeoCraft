package MMC.neocraft.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import MMC.neocraft.lib.Reference;
import MMC.neocraft.network.packet.NCpacket;
import MMC.neocraft.network.packet.PacketTileUpdate;

public enum PacketTypeHandler 
{
    TILE(PacketTileUpdate.class);
    //ITEM_UPDATE(PacketItemUpdate.class),
    //TILE_WITH_ITEM(PacketTileWithItemUpdate.class);

    private Class<? extends NCpacket> packet;

    PacketTypeHandler(Class<? extends NCpacket> packet) 
    {
        this.packet = packet;
    }

    public static NCpacket buildPacket(byte[] data) 
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);
        NCpacket pack = null;

        try { pack = values()[selector].packet.newInstance(); }
        catch (Exception e) { e.printStackTrace(System.err); }
        pack.readPopulate(dis);
        return pack;
    }
    public static NCpacket buildPacket(PacketTypeHandler type) 
    {
    	NCpacket pack = null;

        try { pack = values()[type.ordinal()].packet.newInstance(); }
        catch (Exception e) { e.printStackTrace(System.err); }
        return pack;
    }
    public static Packet populatePacket(NCpacket pack) 
    {
        byte[] data = pack.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Reference.CHANNEL_NAME;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = pack.isChunkDataPacket;

        return packet250;
    }
}
