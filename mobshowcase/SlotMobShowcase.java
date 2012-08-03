package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;

public class SlotMobShowcase extends Slot
{
	public final TileEntityMobShowcase mobShowcase;
	
	public SlotMobShowcase(IInventory par1IInventory, int par2, int par3, int par4, TileEntityMobShowcase par5)
	{
		super(par1IInventory, par2, par3, par4);
		mobShowcase = par5;
	}
	
	public void onSlotChanged()
	{
		super.onSlotChanged();
		
		if (getHasStack())
		{
			int i = getStack().itemID;
			int id = 0;
			NBTTagCompound nbt = null;
			String name = "";
			
			if (i == Item.monsterPlacer.shiftedIndex)
			{
				id = getStack().getItemDamage();
			}
			else if (MobShowcaseList.getInstance().getEntityID(getStack()) > -1)
			{
				id = MobShowcaseList.getInstance().getEntityID(getStack());
				//System.out.println("setID_" + id);
			}
			else if (mod_MobShowcase.existMod)
			{//pokeloli
				mod_PokeloliFigure mod = mod_PokeloliFigure.GetInstance();
				if (i == mod.GetItemObject(mod.GetItemID()).shiftedIndex)
				{
					//System.out.println("setNBT");
					nbt = getStack().getTagCompound();
				}
			}

			if (id != 0)
			{
				name = EntityList.getStringFromID(id);
			}
			else if (nbt != null)
			{
				name = nbt.getString("id");
			}
			
			mobShowcase.setMobName(name);
			mobShowcase.setMobNBT(nbt);
		}
		
		if (!getHasStack())
		{
			mobShowcase.setMobName("");
			mobShowcase.setMobNBT(null);
		}
	}
}