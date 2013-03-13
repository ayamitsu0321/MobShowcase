package ayamitsu.mobshowcase.common;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public final class MobShowcaseRegistry
{
	private static Multimap<Integer, IMobReturner> returnerMap = HashMultimap.create();

	public static void addReturner(int itemID, IMobReturner returner)
	{
		returnerMap.put(Integer.valueOf(itemID), returner);
	}

	public static boolean contains(ItemStack is)
	{
		if (is == null)
		{
			return false;
		}

		return returnerMap.containsKey(Integer.valueOf(is.itemID));
	}

	public static Entity getEntity(ItemStack is, World world)
	{
		Collection<IMobReturner> collection = returnerMap.get(Integer.valueOf(is.itemID));

		if (collection != null && !collection.isEmpty())
		{
			for (Iterator<IMobReturner> iterator = collection.iterator(); iterator.hasNext();)
			{
				IMobReturner returner = iterator.next();
				Entity entity = returner.getEntity(is, world);

				if (entity != null)
				{
					return entity;
				}
			}
		}

		return null;
	}

}