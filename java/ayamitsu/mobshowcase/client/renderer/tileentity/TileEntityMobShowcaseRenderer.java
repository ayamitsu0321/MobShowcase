package ayamitsu.mobshowcase.client.renderer.tileentity;

import ayamitsu.mobshowcase.block.BlockMobShowcase;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by ayamitsu0321 on 2016/03/23.
 */
public class TileEntityMobShowcaseRenderer extends TileEntitySpecialRenderer<TileEntityMobShowcase> {

    @Override
    public void renderTileEntityAt(TileEntityMobShowcase te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
        GlStateManager.translate(0.0F, 0.25F/*block height*/, 0.0F);
        renderMob(te, x, y, z, partialTicks);
        GlStateManager.popMatrix();
    }

    public void renderMob(TileEntityMobShowcase showcase, double x, double y, double z, float partialTicks) {
        Entity entity = showcase.showcaseLogic.theEntity;

        if (entity != null) {
            float f = 0.53125F;
            float f1 = Math.max(entity.width, entity.height);

            if ((double)f1 > 1.0D) {
                f /= f1;
            }

            //GlStateManager.translate(0.0F, 0.4F, 0.0F);
            //GlStateManager.rotate((float)(mobSpawnerLogic.getPrevMobRotation() + (mobSpawnerLogic.getMobRotation() - mobSpawnerLogic.getPrevMobRotation()) * (double)partialTicks) * 10.0F, 0.0F, 1.0F, 0.0F);
            //GlStateManager.translate(0.0F, -0.2F, 0.0F);
            //GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);

            GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(f, f, f);
            entity.setPosition(x, y, z);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F/*partialTicks*/, false);
        }
    }

}
