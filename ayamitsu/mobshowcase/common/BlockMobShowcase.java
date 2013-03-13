package ayamitsu.mobshowcase.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ayamitsu.mobshowcase.MobShowcase;

public class BlockMobShowcase extends BlockContainer
{
	private int renderType;
	public Random random;

	public BlockMobShowcase(int id, Material material, int render)
	{
		super(id, material);
		this.renderType = render;
		this.random = new Random();
		this.setBlockBoundsForItemRender();
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityMobShowcase();
	}

	@Override
    public void breakBlock(World world, int blockX, int blockY, int blockZ, int side, int par6)
	{
		TileEntity tileentity = world.getBlockTileEntity(blockX, blockY, blockZ);

		if (tileentity instanceof TileEntityMobShowcase)
		{
			TileEntityMobShowcase showcase = (TileEntityMobShowcase)tileentity;

			for (int i = 0; i < showcase.getSizeInventory(); i++)
            {
				ItemStack is = showcase.getStackInSlot(i);

                if (is != null)
                {
                    float f = this.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; is.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int j = this.random.nextInt(21) + 10;

                        if (j > is.stackSize)
                        {
                            j = is.stackSize;
                        }

                        is.stackSize -= j;
                        entityitem = new EntityItem(world, (double)((float)blockX + f), (double)((float)blockY + f1), (double)((float)blockZ + f2), new ItemStack(is.itemID, j, is.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                        if (is.hasTagCompound())
                        {
                        	entityitem.getEntityItem().setTagCompound((NBTTagCompound)is.getTagCompound().copy());
                        }
                    }
                }
            }
		}

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
			player.openGui(MobShowcase.instance, 0, world, blockX, blockY, blockZ);
		}

		return true;
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
	{
		return this.renderType == 0 ? Block.glass.getBlockTextureFromSideAndMetadata(par1, par2) : Block.stone.getBlockTextureFromSideAndMetadata(par1, par2);
	}

}