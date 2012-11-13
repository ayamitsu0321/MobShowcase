package ayamitsu.mobshowcase.common;

import net.minecraft.src.*;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void load()
	{
		GameRegistry.registerTileEntity(TileEntityMobShowcase.class, "MobShowcase");
	}
	
	public int getUniqueRenderId()
	{
		return -1;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		/*if (id == 0)
		{
			return new GuiMobShowcase(player, world, x, y, z);
		}*/

		return null;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == 0)
		{
			return new ContainerMobShowcase(player, world, x, y, z);
		}

		return null;
	}
}