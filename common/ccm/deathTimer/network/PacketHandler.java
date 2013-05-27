package ccm.deathTimer.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.timerTypes.BasicTimer;
import ccm.deathTimer.timerTypes.DeathTimer;
import ccm.deathTimer.timerTypes.PointTimer;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Don't forget to add timer types to the list...
 * @author Dries007
 *
 */
public class PacketHandler implements IPacketHandler
{   
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player playerFake)
    {
        if (!packet.channel.equals(Archive.MOD_CHANNEL)) return;
        
        ByteArrayInputStream streambyte = new ByteArrayInputStream(packet.data);
        DataInputStream stream = new DataInputStream(streambyte);

        try
        {
            int ID = stream.readInt();

            switch (ID)
            {
                case BasicTimer.PACKETID:
                    ClientTimer.getInstance().updateTimer(new BasicTimer().getUpdate(stream));
                break;
                case PointTimer.PACKETID:
                    ClientTimer.getInstance().updateTimer(new PointTimer().getUpdate(stream));
                break;
                case DeathTimer.PACKETID:
                    ClientTimer.getInstance().updateTimer(new DeathTimer().getUpdate(stream));
                break;
            }
            
            streambyte.close();
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}