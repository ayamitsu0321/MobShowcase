package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;

public class TileEntityMobShowcase extends TileEntity implements IInventory
{
	private ItemStack mobEgg[];//inventory
	private float scale;
	private float rotateX;
	private int magnification;
	private float translate;
	protected Entity mob;
	//color
	private float colorR;
	private float colorG;
	private float colorB;
	//do rotate
	private boolean isRotate;
	public double yaw;
	public double yaw2;
	private float delay;
	private float field_a;
	
	private String mobName;
	private NBTTagCompound mobNBT;
	
	public TileEntityMobShowcase()
	{
		super();
		mobEgg = new ItemStack[1];
		scale = 1.0F;
		magnification = 1;
		translate = 0.5F;
		rotateX = 0.0F;
		colorR = 1.0F;
		colorG = 1.0F;
		colorB = 1.0F;
		yaw2 = 0.0D;
		delay = 0.0F;
		field_a = 0.0F;
		mobName = "";
	}
	
	public void setMobScale(float par1)
	{
		scale = par1;
	}
	
	public float getMobScale()
	{
		return scale;
	}
	
	protected void setEntity(Entity par1Entity)
	{
		mob = par1Entity;
	}
	
	public Entity getEntity()
	{
		return mob;
	}
	
	public void setMobMagnification(int par1)
	{
		magnification = par1;
	}
	
	public int getMobMagnification()
	{
		return magnification;
	}
	
	public void setMobTranslate(float par1)
	{
		translate = par1;
	}
	
	public float getMobTranslate()
	{
		return translate;
	}
	
	public void setMobRotationX(float par1)
	{
		rotateX = par1;
	}
	
	public float getMobRotationX()
	{
		return rotateX;
	}

	public void setMobColorRGB(float par1, int color)
	{
		switch (color) {
		case 0: colorR = par1; break;
		case 1: colorG = par1; break;
		default: colorB = par1; break;
		}
	}
	
	public float getMobColorRGB(int color)
	{
		switch (color) {
		case 0: return colorR;
		case 1: return colorG;
		default: return colorB;
		}
	}
	
	public void setDoRotation(boolean par1)
	{
		isRotate = par1;
	}

	public boolean isDoRotation()
	{
		return isRotate;
	}
	
	public void setDelay(float par1)
	{
		delay = par1;
	}
	
	public float getDelay()
	{
		return (float)delay;
	}
	
	public void setField_a(float par1)
	{
		field_a = par1;
	}
	
	public float getField_a()
	{
		return field_a;
	}
	
	public void setMobName(String str)
	{
		mobName = str;
	}
	
	public String getMobName()
	{
		return mobName;
	}
	
	public void setMobNBT(NBTTagCompound nbt)
	{
		mobNBT = nbt;
	}
	
	public NBTTagCompound getMobNBT()
	{
		return mobNBT;
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        mobEgg = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound.getByte("Slot");

            if (byte0 >= 0 && byte0 < mobEgg.length)
            {
                mobEgg[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
		
		setMobScale(par1NBTTagCompound.getFloat("EntityScale"));//Scale
		setMobMagnification(par1NBTTagCompound.getInteger("EntityMagnification"));//Magnification
		setMobTranslate(par1NBTTagCompound.getFloat("EntityTranslate"));//Translate
		setMobRotationX(par1NBTTagCompound.getFloat("EntityRotationX"));//RotationX
		setMobColorRGB(par1NBTTagCompound.getFloat("EntityColorR"), 0);//R
		setMobColorRGB(par1NBTTagCompound.getFloat("EntityColorG"), 1);//G
		setMobColorRGB(par1NBTTagCompound.getFloat("EntityColorB"), 2);//B
		setDoRotation(par1NBTTagCompound.getBoolean("isDisplay"));//display
		setDelay(par1NBTTagCompound.getFloat("Delay"));//delay
		setField_a(par1NBTTagCompound.getFloat("Field_a"));//field_a
		setMobName(par1NBTTagCompound.getString("EntityName"));//Name
		//
		if (mod_MobShowcase.existMod)
		{
			if (mobEgg[0] != null && mobEgg[0].getTagCompound() != null && getMobNBT() == null)
			{
				int i = mobEgg[0].itemID;
				mod_PokeloliFigure mod = mod_PokeloliFigure.GetInstance();
				
				if (i == mod.GetItemObject(mod.GetItemID()).shiftedIndex)
				{
					//System.out.println("setNBT");
					setMobNBT(mobEgg[0].getTagCompound());
				}
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < mobEgg.length; i++)
        {
            if (mobEgg[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                mobEgg[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
    	par1NBTTagCompound.setFloat("EntityScale", getMobScale());
    	par1NBTTagCompound.setInteger("EntityMagnification", getMobMagnification());
    	par1NBTTagCompound.setFloat("EntityTranslate", getMobTranslate());
    	par1NBTTagCompound.setFloat("EntityRotationX", getMobRotationX());
    	par1NBTTagCompound.setFloat("EntityColorR", getMobColorRGB(0));//R
    	par1NBTTagCompound.setFloat("EntityColorG", getMobColorRGB(1));//G
    	par1NBTTagCompound.setFloat("EntityColorB", getMobColorRGB(2));//B
    	par1NBTTagCompound.setBoolean("isDisplay", isDoRotation());
    	par1NBTTagCompound.setFloat("Delay", getDelay());
    	par1NBTTagCompound.setFloat("Field_a", getField_a());
    	par1NBTTagCompound.setString("EntityName", getMobName());//Name
    }
	
	public void updateEntity()
	{
		yaw2 = yaw;
		
		if (delay > 0.0F)
		{
			for (yaw += 0.2F * getDelay() * 10F; yaw > 360D;)
        	{
            	yaw -= 360D;
            	yaw2 -= 360D;
        	}
		}
		
		super.updateEntity();
	}
	
//inventory
	public int getSizeInventory()
	{
		return mobEgg.length;
	}
	
	public ItemStack getStackInSlot(int par1)
    {
        return mobEgg[par1];
    }
	
	public ItemStack decrStackSize(int par1, int par2)
    {
        if (mobEgg[par1] != null)
        {
            if (mobEgg[par1].stackSize <= par2)
            {
                ItemStack itemstack = mobEgg[par1];
                mobEgg[par1] = null;
                return itemstack;
            }

            ItemStack itemstack1 = mobEgg[par1].splitStack(par2);

            if (mobEgg[par1].stackSize == 0)
            {
                mobEgg[par1] = null;
            }

            return itemstack1;
        }
        else
        {
            return null;
        }
    }
	
	public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (mobEgg[par1] != null)
        {
            ItemStack itemstack = mobEgg[par1];
            mobEgg[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        mobEgg[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
        {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }
    }
	
	public String getInvName()
    {
        return "Mob Showcase";
    }
	
	public int getInventoryStackLimit()
    {
        return 64;
    }
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }

        return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }
	
	public void openChest()
    {
    }

    public void closeChest()
    {
    }
}