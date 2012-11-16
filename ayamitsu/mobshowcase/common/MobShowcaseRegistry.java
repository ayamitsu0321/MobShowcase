package ayamitsu.mobshowcase.common;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Entity;
import net.minecraft.src.World;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

public final class MobShowcaseRegistry
{
	private static boolean hasInit = false;
	private static Multimap<Integer, IMobReturner> returnerMap;
	
	public static void addReturner(int itemID, IMobReturner returner)
	{
		init();
		returnerMap.put(Integer.valueOf(itemID), returner);
	}
	
	public static boolean contains(ItemStack is)
	{
		init();
		
		if (is == null)
		{
			return false;
		}
		
		return returnerMap.containsKey(Integer.valueOf(is.itemID));
	}
	
	/*public static IMobReturner getMobReturner(ItemStack is)
	{
		Collection<IMobReturner> collection = returnerMap.get(Integer.valueOf(is));
		
		if (collection == null || collection.isEmpty())
		{
			return null;
		}
		else
		{
			for (Iterator iterator = collection.iterator(); iterator.hasNext();)
			{
				IMobReturner returner = iterator.next();
				
				if (returner.getEntity(is) != null)
				{
					return returner;
				}
			}
			
			return null;
		}
	}*/
	
	public static Entity getEntity(ItemStack is, World world)
	{
		init();
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