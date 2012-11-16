package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PacketMobShowcase extends Packet250CustomPayload
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public NBTTagCompound customData;
	
	public PacketMobShowcase(String str, int x, int y, int z, NBTTagCompound nbttagcompound)
	{
		super();
		this.channel = str;
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.customData = nbttagcompound;
		
		int i = x + y + z;
		try
		{
			byte[] b = CompressedStreamTools.compress(nbttagcompound);
			i += b.length;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		this.length = i;
	}
	
	@Override
	public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		super.readPacketData(par1DataInputStream);
		this.xPosition = par1DataInputStream.readInt();
		this.yPosition = par1DataInputStream.readShort();
		this.zPosition = par1DataInputStream.readInt();
		this.customData = readNBTTagCompound(par1DataInputStream);
	}
	
	@Override
	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeInt(this.xPosition);
		par1DataOutputStream.writeShort(this.yPosition);
		par1DataOutputStream.writeInt(this.zPosition);
		writeNBTTagCompound(this.customData, par1DataOutputStream);
	}
}