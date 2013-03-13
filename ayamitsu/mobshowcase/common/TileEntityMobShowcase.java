package ayamitsu.mobshowcase.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;

public class TileEntityMobShowcase extends TileEntity implements IInventory
{
	private ItemStack[] item = new ItemStack[1];
	private float scale = 1.0F;
	private float rotateX = 0.0F;
	private int magnification = 1;
	private float translate = 0.5F;
	protected Entity mob;
	private float[] color = new float[] { 1.0F, 1.0F, 1.0F };
	private boolean isRotate = false;
	public double yaw = 0.0D;
	public double yaw2 = 0.0D;
	private float delay = 0.0F;
	private float field_a;

	protected NBTTagCompound firstLoadedEntityNBT = null;

	public TileEntityMobShowcase()
	{
		super();
	}

	public void setMobScale(float par1)
	{
		this.scale = par1;
	}

	public float getMobScale()
	{
		return this.scale;
	}

	public void setEntity(Entity par1Entity)
	{
		this.mob = par1Entity;
	}

	public Entity getEntity()
	{
		return this.mob;
	}

	public void setMobMagnification(int par1)
	{
		this.magnification = par1;
	}

	public int getMobMagnification()
	{
		return this.magnification;
	}

	public void setMobTranslate(float par1)
	{
		this.translate = par1;
	}

	public float getMobTranslate()
	{
		return this.translate;
	}

	public void setMobRotationX(float par1)
	{
		this.rotateX = par1;
	}

	public float getMobRotationX()
	{
		return this.rotateX;
	}

	public void setMobColorRGB(float par1, int par2)
	{
		this.color[par2] = par1;
	}

	public float getMobColorRGB(int par1)
	{
		return this.color[par1];
	}

	public void setDoRotation(boolean par1)
	{
		this.isRotate = par1;
	}

	public boolean isDoRotation()
	{
		return this.isRotate;
	}

	public void setDelay(float par1)
	{
		this.delay = par1;
	}

	public float getDelay()
	{
		return (float)this.delay;
	}

	public void setField_a(float par1)
	{
		field_a = par1;
	}

	public float getField_a()
	{
		return field_a;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.item = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < this.item.length)
			{
				this.item[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		this.scale = par1NBTTagCompound.getFloat("EntityScale");
		this.magnification = par1NBTTagCompound.getInteger("EntityMagnification");
		this.translate = par1NBTTagCompound.getFloat("EntityTranslate");
		this.rotateX = par1NBTTagCompound.getFloat("EntityRotationX");
		this.color[0] = par1NBTTagCompound.getFloat("EntityColorR");//R
		this.color[1] = par1NBTTagCompound.getFloat("EntityColorG");//G
		this.color[2] = par1NBTTagCompound.getFloat("EntityColorB");//B
		this.isRotate = par1NBTTagCompound.getBoolean("isDisplay");//display
		this.delay = par1NBTTagCompound.getFloat("Delay");//delay
		NBTTagList nbttaglist2 = par1NBTTagCompound.getTagList("Color");

		try
		{
			for (int j = 0; j < nbttaglist2.tagCount(); j++)
			{
				this.color[j] = ((NBTTagFloat)nbttaglist2.tagAt(j)).data;
			}
		}
		catch (ReportedException e)
		{
			for (int j = 0; j < this.color.length; j++)
			{
				this.color[j] = 1.0F;
			}
		}

		this.isRotate = par1NBTTagCompound.getBoolean("isDisplay");
		this.delay = par1NBTTagCompound.getFloat("Delay");

		NBTTagCompound entityNBT = par1NBTTagCompound.getCompoundTag("Mob");
		NBTTagCompound dataWatcherNBT = par1NBTTagCompound.getCompoundTag("DataWatcher");

		if (entityNBT != null)
		{
			if (this.worldObj != null)
			{
				this.mob = EntityList.createEntityFromNBT(entityNBT, this.worldObj);
			}
			else
			{
				this.firstLoadedEntityNBT = entityNBT;
			}
		}
		else
		{
			this.mob = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < item.length; i++)
		{
			if (item[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				item[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
    	par1NBTTagCompound.setFloat("EntityScale", this.scale);
    	par1NBTTagCompound.setInteger("EntityMagnification", this.magnification);
    	par1NBTTagCompound.setFloat("EntityTranslate", this.translate);
    	par1NBTTagCompound.setFloat("EntityRotationX", this.rotateX);

		par1NBTTagCompound.setFloat("EntityColorR", this.color[0]);//R
		par1NBTTagCompound.setFloat("EntityColorG", this.color[1]);//G
		par1NBTTagCompound.setFloat("EntityColorB", this.color[2]);//B
		par1NBTTagCompound.setBoolean("isDisplay", this.isRotate);
		par1NBTTagCompound.setFloat("Delay", this.delay);

		NBTTagList nbttaglist2 = new NBTTagList();

		for (int j = 0; j < this.color.length; j++)
		{
			nbttaglist2.appendTag(new NBTTagFloat((String)null, this.color[j]));
		}

    	par1NBTTagCompound.setBoolean("isDisplay", this.isRotate);
    	par1NBTTagCompound.setFloat("Delay", this.delay);

		NBTTagCompound entityNBT = new NBTTagCompound();
		NBTTagCompound dataWatcherNBT = new NBTTagCompound();

		if (this.mob != null)
		{
			this.mob.writeToNBT(entityNBT);
			entityNBT.setString("id", EntityList.getEntityString(this.mob));
		}

		par1NBTTagCompound.setCompoundTag("Mob", entityNBT);
		par1NBTTagCompound.setCompoundTag("DataWatcher", dataWatcherNBT);
	}

	@Override
	public void updateEntity()
	{
		if (this.worldObj != null && this.firstLoadedEntityNBT != null)
		{
			this.mob = EntityList.createEntityFromNBT(this.firstLoadedEntityNBT, this.worldObj);
			this.firstLoadedEntityNBT = null;
		}

		this.yaw2 = this.yaw;

		if (this.delay > 0.0F)
		{
			for (this.yaw += 0.2F * this.getDelay() * 10F; this.yaw > 360D;)
        	{
            	this.yaw -= 360D;
            	this.yaw2 -= 360D;
        	}
		}

		super.updateEntity();
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
    	this.readFromNBT(packet.customParam1);
    }

// inventory

	public int getSizeInventory()
	{
		return item.length;
	}

	public ItemStack getStackInSlot(int par1)
    {
        return item[par1];
    }

	public ItemStack decrStackSize(int par1, int par2)
    {
        if (item[par1] != null)
        {
            if (item[par1].stackSize <= par2)
            {
                ItemStack itemstack = item[par1];
                item[par1] = null;
                return itemstack;
            }

            ItemStack itemstack1 = item[par1].splitStack(par2);

            if (item[par1].stackSize == 0)
            {
                item[par1] = null;
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
        if (item[par1] != null)
        {
            ItemStack itemstack = item[par1];
            item[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        item[par1] = par2ItemStack;

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
//
	public void openChest() {}

    public void closeChest() {}

	@Override
	public boolean func_94042_c()// hasCustomName
	{
		return false;
	}

	@Override
	public boolean func_94041_b(int slotIndex, ItemStack itemstack)// canPutItemInSlot
	{
		return slotIndex == 0 && itemstack != null && MobShowcaseRegistry.contains(itemstack);
	}
}