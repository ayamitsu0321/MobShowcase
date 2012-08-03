package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;
import java.util.Random;

public class BlockMobShowcase extends BlockContainer
{
	private Random random;
	private int renderType;
	
	public BlockMobShowcase(int par1, int par2, Material material, int render)
	{
		super(par1, par2, material);
		random = new Random();
		renderType = render;
	}
	
	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		if (par1World.isRemote)
        {
            return true;
        }
		
		TileEntityMobShowcase mobShowcase = (TileEntityMobShowcase)par1World.getBlockTileEntity(par2, par3, par4);
		
		if (mobShowcase != null)
		{
			ModLoader.openGUI(par5EntityPlayer, new GuiMobShowcase(par5EntityPlayer.inventory, mobShowcase));
		}
		
		return true;
	}
	
	public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        TileEntityMobShowcase mobShowcase = (TileEntityMobShowcase)par1World.getBlockTileEntity(par2, par3, par4);

        if (mobShowcase != null)
        {
            for (int i = 0; i < mobShowcase.getSizeInventory(); i++)
            {
                ItemStack itemstack = mobShowcase.getStackInSlot(i);

                if (itemstack == null)
                {
                    continue;
                }

                float f = random.nextFloat() * 0.8F + 0.1F;
                float f1 = random.nextFloat() * 0.8F + 0.1F;
                float f2 = random.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0)
                {
                    int j = random.nextInt(21) + 10;

                    if (j > itemstack.stackSize)
                    {
                        j = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(par1World, (float)par2 + f, (float)par3 + f1, (float)par4 + f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float)random.nextGaussian() * f3;
                    entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)random.nextGaussian() * f3;

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.item.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }

                    par1World.spawnEntityInWorld(entityitem);
                }
            }
        }

        super.onBlockRemoval(par1World, par2, par3, par4);
    }
	
	public TileEntity getBlockEntity()
    {
        return new TileEntityMobShowcase();
    }
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public int getRenderType()
	{
		return renderType;
	}
	
	public void setBlockBoundsForItemRender()
    {
        if (renderType == mod_MobShowcase.stageRender)
        {
            setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
        }
    	else
    	{
    		super.setBlockBoundsForItemRender();
    	}
    }
	
	public boolean isOpaqueCube()
    {
        return false;
    }
	
	protected boolean func_50074_q()
    {
        return true;
    }
}