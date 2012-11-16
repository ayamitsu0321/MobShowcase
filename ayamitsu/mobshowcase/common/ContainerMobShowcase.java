package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;
import cpw.mods.fml.common.network.PacketDispatcher;

import java.io.IOException;

public class ContainerMobShowcase extends Container
{
	public TileEntityMobShowcase showcase;
	private boolean slotChanged = false;
	
	public ContainerMobShowcase(EntityPlayer player, World world, int blockX, int blockY, int blockZ)
	{
		this.showcase = (TileEntityMobShowcase)world.getBlockTileEntity(blockX, blockY, blockZ);
		this.addSlotToContainer(new SlotMobShowcase(this.showcase, 0, 152, 62));
		
		for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                this.addSlotToContainer(new Slot(player.inventory, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.showcase.isUseableByPlayer(par1EntityPlayer);
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		ItemStack is = null;
		Slot slot = (Slot)this.inventorySlots.get(slotIndex);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();
			
			if (slotIndex == 0)
			{
				if (!this.mergeItemStack(is1, 1, 37, true))
				{
					return null;
				}
				
				slot.onSlotChange(is1, is);
			}
			else if (slotIndex > 0 && slotIndex < 28)
			{
				if (MobShowcaseRegistry.contains(is1))
				{
					if (!this.mergeItemStack(is1, 0, 1, false))
					{
						return null;
					}
				}
				else if (!this.mergeItemStack(is1, 28, 37, false))
				{
					return null;
				}
			}
			else if (slotIndex >= 28 && slotIndex < 37 && !this.mergeItemStack(is1, 1, 28, false))
			{
				return null;
			}
			
			if (is1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (is1.stackSize == is.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, is1);
		}
		
		return is;
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer player)
	{
		super.onCraftGuiClosed(player);
		SlotMobShowcase.hasInit = 0;
		System.out.println("on close");
		
		if (this.showcase.getWorldObj().isRemote)
		{
			System.out.println("on close remote");
			
			try
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				this.showcase.writeToNBT(nbttagcompound);
				byte[] data = CompressedStreamTools.compress(nbttagcompound);
				PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("mobshowcase", data));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			//PacketDispatcher.sendPacketToServer(new PacketMobShowcase("Slot", this.showcase.xCoord, this.showcase.yCoord, this.showcase.zCoord, nbttagcompound));
			//PacketDispatcher.sendPacketToServer(this.showcase.getDescriptionPacket());
		}
	}
}