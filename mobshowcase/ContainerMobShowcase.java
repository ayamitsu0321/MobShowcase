package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;

public class ContainerMobShowcase extends Container
{
	protected TileEntityMobShowcase mobShowcase;
	
	public ContainerMobShowcase(InventoryPlayer par1InventoryPlayer, TileEntityMobShowcase par2)
	{
		mobShowcase = par2;
		SlotMobShowcase slot = new SlotMobShowcase(par2, 0, 152, 62, par2);
		addSlot(slot);
		
		for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
                addSlot(new Slot(par1InventoryPlayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
            addSlot(new Slot(par1InventoryPlayer, j, 8 + j * 18, 142));
        }
	}
	
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return mobShowcase.isUseableByPlayer(par1EntityPlayer);
    }
	
	public ItemStack transferStackInSlot(int par1)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(par1);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par1 == 0)
            {
                if (!mergeItemStack(itemstack1, 1, 37, false))//boolean ‚Í¸‡‚©~‡
                {
                    return null;
                }
            }
        	else if (par1 >= 0 && par1 < 28)
            {
            	Item item = itemstack1.getItem();
            	int i = itemstack1.itemID;
            	int j = itemstack1.getItemDamage();
            	Integer[] k = {i, j};
            	
            	if (item instanceof ItemMonsterPlacer || MobShowcaseList.getInstance().getEntityID(itemstack1) > -1)
            	{
            		if (!mergeItemStack(itemstack1, 0, 1, false))
            		{
            			return null;
            		}
            	}
                else if (!mergeItemStack(itemstack1, 28, 37, false))
                {
                    return null;
                }
            }
            else if (par1 >= 28 && par1 < 37 && !mergeItemStack(itemstack1, 1, 28, false))
            {
                return null;
            }
        	
        	if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize != itemstack.stackSize)
            {
                slot.onPickupFromSlot(itemstack1);
            }
            else
            {
                return null;
            }
        }

        return itemstack;
    }
}