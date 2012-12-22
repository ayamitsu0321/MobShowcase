package ayamitsu.mobshowcase.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MobReturnerEgg implements IMobReturner
{
	/**
	 * ���܂�, �g���ƃh���S��
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