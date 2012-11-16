package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

public class MobReturnerSpawnEgg implements IMobReturner
{
	/**
	 * �X�|�[���G�b�O����ID�𔻕ʂ��Ă�����
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