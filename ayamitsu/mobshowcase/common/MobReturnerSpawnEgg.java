package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class MobReturnerSpawnEgg implements IMobReturner
{
	/**
	 * スポーンエッグからIDを判別してかえす
	 */
	
	public MobReturnerSpawnEgg() {}
	
	public Entity getEntity(ItemStack is, World world)
	{
		if (is.itemID != Item.monsterPlacer.shiftedIndex)
		{
			return null;
		}
		else
		{
			return EntityList.entityEggs.containsKey(is.getItemDamage()) ? EntityList.createEntityByID(is.getItemDamage(), world) : null;
		}
	}
}