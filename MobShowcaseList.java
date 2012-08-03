package net.minecraft.src;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class MobShowcaseList
{
	//ms = MobShowcase
	private static final MobShowcaseList instance = new MobShowcaseList();
	private Map<Class, List> msClassToItemMapping;//List�Ƃ������AItemStack�擾�̂���
	private Map<List, Class> msItemToClassMapping;//Class�擾�̂���
	
	//Instance�擾
	public static final MobShowcaseList getInstance()
	{
		return instance;
	}
	
	private MobShowcaseList()
	{
		msClassToItemMapping = new HashMap();
		msItemToClassMapping = new HashMap();
	}
	
	/**
	 * Map�ɓo�^
	 * args:Entity's Class, ItemID, ItemDamage
	 */
	public void addMSMapping(Class clazz, int itemID, int damage)
	{
		msClassToItemMapping.put(clazz, Arrays.asList(itemID, damage));
		msItemToClassMapping.put(Arrays.asList(itemID, damage), clazz);
	}
	
	/**
	 * Item��ID�ƃ_���[�W����Entity��Class�擾���āA���ꂩ��Entity��ID��Ԃ�
	 * ItemStack...Class...EntityID
	 * args:ItemStack
	 */
	public int getEntityID(ItemStack is)
	{
		Class clazz = (Class)msItemToClassMapping.get(Arrays.asList(is.itemID, is.getItemDamage()));
		
		try
		{
			HashMap map = (HashMap)ModLoader.getPrivateValue(EntityList.class, null, 3);
			return (Integer)map.get(clazz);
		}
		catch (Exception e)
		{
			//System.out.println("Bad...");
			return -1;
		}
	}
	
	/**
	 * Item��ID�ƃ_���[�W����Entity��Class�擾���āA���ꂩ��Entity��ID��Ԃ�
	 * id&damage...Class...EntityID
	 * args:ItemStack
	 */
	public int getEntityID(int itemID, int damage)
	{
		Class clazz = (Class)msItemToClassMapping.get(Arrays.asList(itemID, damage));
		
		try
		{
			HashMap map = (HashMap)ModLoader.getPrivateValue(EntityList.class, null, 3);
			return (Integer)map.get(clazz);
		}
		catch (Exception e)
		{
			//System.out.println("Bad...");
			return -1;
		}
	}
	
	/**
	 * Item��ID��Damage����Map����Class�擾
	 * args:ItemID
	 */
	public Class getEntityClass(int itemID, int damage)
	{
		return (Class)msItemToClassMapping.get(Arrays.asList(itemID, damage));
	}
	
	/**
	 * Class����ItemStack�擾
	 * args:Entity's Class
	 */
	public ItemStack getItemStack(Class clazz)
	{
		List list = (List)msClassToItemMapping.get(clazz);
		ItemStack is = new ItemStack((Integer)list.get(0), 1, (Integer)list.get(1));
		return is;
	}
}