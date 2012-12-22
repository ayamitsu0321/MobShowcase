package ayamitsu.mobshowcase.common;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet250, Player player)
	{
		//System.out.println("onPacketData@" + packet250.getClass().toString() + "," + String.valueOf(player));

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
	}
}