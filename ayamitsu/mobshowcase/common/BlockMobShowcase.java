package ayamitsu.mobshowcase.common;

import ayamitsu.mobshowcase.MobShowcase;

import net.minecraft.src.*;

import java.util.Random;

public class BlockMobShowcase extends BlockContainer
{
	private int renderType;
	
	public BlockMobShowcase(int id, int tex, Material material, int render)
	{
		super(id, tex, material);
		this.renderType = render;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityMobShowcase();
	}
	
	@Override
    public void breakBlock(World world, int blockX, int blockY, int blockZ, int side, int par6)
	{
		super.breakBlock(world, blockX, blockY, blockZ, side, par6);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return renderType;
	}
	
	@Override
	public void setBlockBoundsForItemRender()
    {
    	if (this.renderType != 0)
    	{
    		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
    	}
    	else
    	{
    		super.setBlockBoundsForItemRender();
    	}
    }
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
	protected boolean canSilkHarvest()
    {
        return true;
    }
	
	@Override
	public boolean onBlockActivated(World world, int blockX, int blockY, int blockZ, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileentity = world.getBlockTileEntity(blockX, blockY, blockZ);
		
		if (world.isRemote)
		{
			return true;
		}
		else if (tileentity instanceof TileEntityMobShowcase)
		{
			// open gui
			System.out.println("activated");
			player.openGui(MobShowcase.instance, 0, world, blockX, blockY, blockZ);
		}
		
		return true;
	}

}