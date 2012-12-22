package ayamitsu.mobshowcase.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

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