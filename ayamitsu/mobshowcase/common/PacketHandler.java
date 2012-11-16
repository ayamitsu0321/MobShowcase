package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.network.IPacketHandler;

import java.io.IOException;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet250, Player player)
	{
		System.out.println("onPacketData@" + packet250.getClass().toString() + "," + String.valueOf(player));
		
		if (packet250.channel.equals("mobshowcase") && player instanceof EntityPlayer)
		{
			byte[] data = packet250.data;
			
			try
			{
				NBTTagCompound nbttagcompound = CompressedStreamTools.decompress(data);
				int xCoord = nbttagcompound.getInteger("x");
				int yCoord = nbttagcompound.getInteger("y");
				int zCoord = nbttagcompound.getInteger("z");
				World world = ((EntityPlayer)player).worldObj;
				TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
				
				if (tile instanceof TileEntityMobShowcase)
				{
					tile.readFromNBT(nbttagcompound);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		/*if (packet250 instanceof PacketMobShowcase && player instanceof EntityPlayer)
		{
			System.out.println("Packet:" + packet250.channel);
			PacketMobShowcase packet = (PacketMobShowcase)packet250;
			World world = ((EntityPlayer)player).worldObj;
			TileEntity tile = world.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);
			tile.readFromNBT(packet.customData);
		}*/
	}
}