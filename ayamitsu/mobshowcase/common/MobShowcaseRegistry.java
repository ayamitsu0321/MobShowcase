package ayamitsu.mobshowcase.common;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public final class MobShowcaseRegistry
{
	private static boolean hasInit = false;
	private static Multimap<Integer, IMobReturner> returnerMap;

	public static void addReturner(int itemID, IMobReturner returner)
	{
		init();
		returnerMap.put(Integer.valueOf(itemID), returner);
	}

	public static boolean contains(net.minecraft.item.ItemStack is)
	{
		init();

		if (is == null)
		{
			return false;
		}

		return returnerMap.containsKey(Integer.valueOf(is.itemID));
	}

	public static net.minecraft.entity.Entity getEntity(net.minecraft.item.ItemStack is, net.minecraft.world.World world)
	{
		init();
		Collection<IMobReturner> collection = returnerMap.get(Integer.valueOf(is.itemID));

		if (collection != null && !collection.isEmpty())
		{
			for (Iterator<IMobReturner> iterator = collection.iterator(); iterator.hasNext();)
			{
				IMobReturner returner = iterator.next();
				net.minecraft.entity.Entity entity = returner.getEntity(is, world);

				if (entity != null)
				{
					return entity;
				}
			}
		}

		return null;
	}

	private static void init()
	{
		if (hasInit)
		{
			return;
		}

		hasInit = true;
		returnerMap = HashMultimap.create();
	}
}