package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class SlotMobShowcase extends Slot
{
	public TileEntityMobShowcase showcase;
	
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
	
	public void onSlotChanged()
	{
		super.onSlotChanged();
		Entity entity = null;
		
		if (this.getHasStack())
		{
			entity = MobShowcaseRegistry.getEntity(this.getStack(), this.showcase.getWorldObj());
		}
		
		this.showcase.setEntity(entity);
	}
}