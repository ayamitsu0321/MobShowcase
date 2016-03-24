package ayamitsu.mobshowcase.inventory;

import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by ayamitsu0321 on 2016/03/23.
 */
public class ContainerMobShowcase extends Container {

    protected TileEntityMobShowcase theShowcase;

    public ContainerMobShowcase(InventoryPlayer playerInventory, TileEntityMobShowcase mobShowcase) {
        this.theShowcase = mobShowcase;

        this.addSlotToContainer(new SlotMobShowcase(mobShowcase, 0, 8, 62));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.theShowcase.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null) {
            if (slot.getHasStack()) {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();

                if (index == 0) {
                    if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                        return null;
                    }

                    slot.onSlotChange(itemstack1, itemstack);
                } else if (index >= 1 && index < 28 && !this.mergeItemStack(itemstack1, 0, 1, false)) {
                    return null;
                } else if (index >= 28 && index < 37 && !this.mergeItemStack(itemstack1, 1, 28, false)) {
                    return null;
                }

                if (itemstack1.stackSize == 0) {
                    slot.putStack((ItemStack)null);
                } else {
                    slot.onSlotChanged();
                }

                if (itemstack1.stackSize == itemstack.stackSize) {
                    return null;
                }

                slot.onPickupFromSlot(playerIn, itemstack1);
            } else {
                slot.onSlotChanged();
            }

        }

        return itemstack;
    }

}
