package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class MobReturnerEgg implements IMobReturner
{
	/**
	 * ‚½‚Ü‚², ƒgƒŠ‚Æƒhƒ‰ƒSƒ“
	 */
	
	public MobReturnerEgg() {}
	
	public Entity getEntity(ItemStack is, World world)
	{
		EntityLiving entity = null;
		
		if (is.itemID == Item.egg.shiftedIndex)
		{
			entity = new EntityChicken(world);
			entity.initCreature();
		}
		else if (is.itemID == Block.dragonEgg.blockID)
		{
			entity = new EntityDragon(world);
			entity.initCreature();
		}
		
		return entity;
	}
}