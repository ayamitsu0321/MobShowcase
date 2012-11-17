package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class ContainerMobShowcase extends Container
{
	public TileEntityMobShowcase showcase;
	
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
		// Š„‚Æd—v
		SlotMobShowcase.hasInit = 0;
	}
	
	@Override
	public void updateCraftingResults()
	{
		super.updateCraftingResults();
	}
}