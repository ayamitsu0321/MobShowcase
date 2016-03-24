package ayamitsu.mobshowcase.inventory;

import ayamitsu.mobshowcase.registry.MobShowcaseRegistry;
import ayamitsu.mobshowcase.tileentity.MobShowcaseBaseLogic;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;

/**
 * Created by ayamitsu0321 on 2016/03/24.
 */
public class SlotMobShowcase extends Slot {

    protected TileEntityMobShowcase theShowcase;

    public SlotMobShowcase(TileEntityMobShowcase showcase, int index, int xPosition, int yPosition) {
        super(showcase, index, xPosition, yPosition);
        this.theShowcase = showcase;
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();

        if (this.getHasStack()) {
            ItemStack is = this.getStack();

            if (MobShowcaseRegistry.contains(is)) {
                Entity entity = MobShowcaseRegistry.getEntity(is);
                this.theShowcase.showcaseLogic.theEntity = entity;
            } else {
                this.theShowcase.showcaseLogic.theEntity = null;
            }
        } else {
            this.theShowcase.showcaseLogic.theEntity = null;
        }

        this.theShowcase.showcaseLogic.onEntityChanged();
    }
}
