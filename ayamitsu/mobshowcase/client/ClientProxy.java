package ayamitsu.mobshowcase.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ayamitsu.mobshowcase.MobShowcase;
import ayamitsu.mobshowcase.common.CommonProxy;
import ayamitsu.mobshowcase.common.TileEntityMobShowcase;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void load()
	{
		ClientRegistry.registerTileEntity(TileEntityMobShowcase.class, "MobShowcase", new TileEntityMobShowcaseRenderer());
		RenderingRegistry.registerBlockHandler(new RenderShowcase(MobShowcase.renderId));
	}

	@Override
	public int getUniqueRenderId()
	{
		return RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == 0)
		{
			return new GuiMobShowcase(player, world, x, y, z);
		}

		return null;
	}
}