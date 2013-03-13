package ayamitsu.mobshowcase.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MobReturnerSpawnEgg implements IMobReturner
{
	/**
	 * �X�|�[���G�b�O����ID�𔻕ʂ��Ă�����
	 */

	public MobReturnerSpawnEgg() {}

	public Entity getEntity(ItemStack is, World world)
	{
		if (is.itemID != Item.monsterPlacer.itemID)
		{
			return null;
		}
		else
		{
			if (EntityList.entityEggs.containsKey(is.getItemDamage()))
			{
				Entity entity = EntityList.createEntityByID(is.getItemDamage(), world);

				if (entity instanceof EntityLiving)
				{
					((EntityLiving)entity).initCreature();
				}

				return entity;
			}
			else
			{
				return null;
			}
		}
	}
}