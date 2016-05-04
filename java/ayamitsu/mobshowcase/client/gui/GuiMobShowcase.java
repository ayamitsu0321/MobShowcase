package ayamitsu.mobshowcase.client.gui;

import ayamitsu.mobshowcase.MobShowcase;
import ayamitsu.mobshowcase.inventory.ContainerMobShowcase;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ayamitsu0321 on 2016/03/23.
 */
public class GuiMobShowcase extends GuiContainer {

    protected static final ResourceLocation showcaseGuiTexture = new ResourceLocation(MobShowcase.MODID, "textures/gui/container/mob_showcase.png");

    protected InventoryPlayer playerInventory;
    protected TileEntityMobShowcase theShowcase;

    public GuiMobShowcase(InventoryPlayer playerInventory, TileEntityMobShowcase mobShowcase) {
        super(new ContainerMobShowcase(playerInventory, mobShowcase));
        this.playerInventory = playerInventory;
        this.theShowcase = mobShowcase;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(showcaseGuiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (this.theShowcase.showcaseLogic.theEntity != null) {
            Entity entity = this.theShowcase.showcaseLogic.theEntity;
            drawEntityOnScreen(i + 51, j + 75, 100, entity);
        }
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, Entity entity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);

        float f = 0.53125F;
        float f1 = Math.max(entity.width, entity.height);

        if ((double)f1 > 1.0D) {
            f /= f1;
        }

        GlStateManager.scale(f, f, f);

        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
        rendermanager.setRenderShadow(true);

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

}
