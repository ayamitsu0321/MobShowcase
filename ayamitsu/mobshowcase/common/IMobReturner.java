package ayamitsu.mobshowcase.common;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Entity;
import net.minecraft.src.World;

public interface IMobReturner
{
	/**
	 * null���������Ζ���
	 */
	public Entity getEntity(ItemStack is, World world);
}