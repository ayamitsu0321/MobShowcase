package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class SlotMobShowcase extends Slot
{
	public TileEntityMobShowcase showcase;
	public static int hasInit = 0;
	
	public SlotMobShowcase(TileEntityMobShowcase par1TileEntityMobShowcase, int par2, int par3, int par4)
	{
		super((IInventory)par1TileEntityMobShowcase, par2, par3, par4);
		this.showcase = par1TileEntityMobShowcase;
	}
	
	@Override
	public boolean isItemValid(ItemStack is)
	{
		return MobShowcaseRegistry.contains(is);
	}
	
	@Override
	public void onSlotChanged()
	{
		super.onSlotChanged();
		System.out.println("onSlotChanged");
		
		if (hasInit < 2)
		{
			++hasInit;
			System.out.println("++hasInit:" + hasInit);
			return;
		}
		
		System.out.println("through");
		Entity entity = null;
		
		if (this.getHasStack())
		{
			System.out.println("new Entity");
			entity = MobShowcaseRegistry.getEntity(this.getStack(), this.showcase.getWorldObj());
		}
		
		this.showcase.setEntity(entity);
		
		/*if (this.showcase.getWorldObj().isRemote)
		{
			System.out.println("send_Packet");
			// Client‚©‚çServer‚Ö‚Æ“¯Šú‚³‚¹‚é‚½‚ß
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			this.showcase.writeToNBT(nbttagcompound);

			PacketDispatcher.sendPacketToServer(new PacketMobShowcase("Slot", this.showcase.xCoord, this.showcase.yCoord, this.showcase.zCoord, nbttagcompound));
			//PacketDispatcher.sendPacketToServer(this.showcase.getDescriptionPacket());
		}*/
	}
}