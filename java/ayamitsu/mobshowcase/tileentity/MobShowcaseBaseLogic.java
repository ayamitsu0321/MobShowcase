package ayamitsu.mobshowcase.tileentity;

import ayamitsu.mobshowcase.block.BlockMobShowcase;
import ayamitsu.mobshowcase.registry.MobShowcaseRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by ayamitsu0321 on 2016/03/23.
 */
public abstract class MobShowcaseBaseLogic {

    public Entity theEntity;

    protected double rotation;
    protected double prevRotation;
    protected double rotationSpeed;

    public void updateShowcase() {
        if (this.getWorld().isRemote) {
            this.prevRotation = this.rotation;
        }
    }

    public void onEntityChanged() {
        if (this.theEntity != null) {
            this.theEntity.rotationPitch = 0.0F;
            this.theEntity.rotationYaw = 0.0F;

            EnumFacing face = this.getWorld().getBlockState(this.getPosition()).getValue(BlockMobShowcase.FACING);

            switch (face) {
                case NORTH: theEntity.rotationYaw = 180F;break;
                case SOUTH: theEntity.rotationYaw = 0F;break;
                case WEST: theEntity.rotationYaw = 270F;break;
                case EAST: theEntity.rotationYaw = 90F;break;
            }
        }

        // server -> client
        if (!this.getWorld().isRemote) {
            this.getWorld().playerEntities.stream().filter(o -> o instanceof EntityPlayerMP).forEach(o -> ((EntityPlayerMP)o).playerNetServerHandler.sendPacket(this.getShowcase().getDescriptionPacket()));
        }
    }

    public void onLoad() {

    }

    public abstract World getWorld();

    public abstract BlockPos getPosition();

    public abstract TileEntityMobShowcase getShowcase();

    public void writeToNBT(NBTTagCompound nbt) {
        if (theEntity != null) {
            NBTTagCompound entityNBT = new NBTTagCompound();
            theEntity.writeToNBT(entityNBT);
            entityNBT.setString("id", EntityList.getEntityString(theEntity));
            nbt.setTag("Mob", entityNBT);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("Mob", 10/*NBTTagCompound*/)) {
            NBTTagCompound entityNBT = nbt.getCompoundTag("Mob");
            this.theEntity = EntityList.createEntityFromNBT(entityNBT, MobShowcaseRegistry.dummyWorld);
        } else {
            this.theEntity = null;
        }
    }

}
