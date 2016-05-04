package ayamitsu.mobshowcase.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by ayamitsu0321 on 2016/03/23.
 */
public class TileEntityMobShowcase extends TileEntityLockable implements ITickable {

    public final MobShowcaseBaseLogic showcaseLogic = new MobShowcaseBaseLogic() {

        @Override
        public World getWorld() {
            return TileEntityMobShowcase.this.worldObj;
        }

        @Override
        public BlockPos getPosition() {
            return TileEntityMobShowcase.this.pos;
        }

        @Override
        public TileEntityMobShowcase getShowcase() {
            return TileEntityMobShowcase.this;
        }

    };

    private ItemStack[] inventory;

    public TileEntityMobShowcase() {
        this.inventory = new ItemStack[1];
    }

    @Override
    public void update() {
        this.showcaseLogic.updateShowcase();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.showcaseLogic.onLoad();

        if (!this.getWorld().isRemote) {

            // server -> client
            //FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendPacketToAllPlayers(this.getDescriptionPacket());
        }
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    @Override
    public String getGuiID() {
        return null;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.inventory[index] != null) {
            if (this.inventory[index].stackSize <= count) {
                ItemStack stack = this.inventory[index];
                this.inventory[index] = null;
                return stack;
            }

            ItemStack stack = this.inventory[index].splitStack(count);

            if (this.inventory[index].stackSize == 0) {
                this.inventory[index] = null;
            }

            return stack;
        }

        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = this.inventory[index];
        this.inventory[index] = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index < this.inventory.length;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int index = 0; index < this.inventory.length; index++) {
            this.inventory[index] = null;
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("Logic", 10/*NBTTagCompound*/)) {
            NBTTagCompound logicNBT = compound.getCompoundTag("Logic");
            this.showcaseLogic.readFromNBT(logicNBT);
        }

        NBTTagList nbttaglist = compound.getTagList("Items", 10/*NBTTagCompound*/);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            byte b = nbttagcompound.getByte("Slot");

            if (b >= 0 && b < this.inventory.length) {
                this.inventory[b] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound logicNBT = new NBTTagCompound();
        this.showcaseLogic.writeToNBT(logicNBT);
        compound.setTag("Logic", logicNBT);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);
    }

    @Override
    public Packet<?> getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new SPacketUpdateTileEntity(this.pos, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

}
