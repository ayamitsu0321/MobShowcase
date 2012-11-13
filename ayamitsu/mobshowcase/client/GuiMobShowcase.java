package ayamitsu.mobshowcase.client;

import ayamitsu.mobshowcase.*;
import ayamitsu.mobshowcase.common.*;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiMobShowcase extends GuiContainer
{
	public TileEntityMobShowcase showcase;
	
	public GuiMobShowcase(EntityPlayer player, World world, int blockX, int blockY, int blockZ)
	{
		super(new ContainerMobShowcase(player, world, blockX, blockY, blockZ));
		showcase = (TileEntityMobShowcase)world.getBlockTileEntity(blockX, blockY, blockZ);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		this.controlList.clear();
	}
	
	/** abstract */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/ayamitsu/mobshowcase/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
		Entity entity = showcase.getEntity();
		
		if (entity != null)
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(j + 46, k + 75, 50F);
			float defScale = 60F;
        	float scale = defScale * showcase.getMobScale() * showcase.getMobMagnification();
            GL11.glScalef(-scale, scale, scale);
        	GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        	//âÒì]
        	GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        	//RenderHelper.enableStandardItemLighting();
        	float rotation = (float)(showcase.yaw2 + (showcase.yaw - showcase.yaw2) * (double)showcase.getField_a()) * 10F;
        	float f4 = showcase.isDoRotation() ? rotation : -rotation;
        	GL11.glRotatef(showcase.getMobRotationX() - 135F + f4, 0.0F, 1.0F, 0.0F);
        	float advance = entity instanceof EntitySquid ? 1.2F : entity instanceof EntityGhast || entity instanceof EntityDragon ? 2.8F : 0.0F;
        	GL11.glTranslatef(0.0F, entity.yOffset + advance, 0.0F);
        	//êF
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	GL11.glColor4f(1.0F * showcase.getMobColorRGB(0), 1.0F * showcase.getMobColorRGB(1), 1.0F * showcase.getMobColorRGB(2), 1.0F);
        	RenderManager.instance.playerViewY = 180F;
        	//Entityï`âÊÇÃñ{ëÃ
            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	GL11.glPopMatrix();
    		//RenderHelper.disableStandardItemLighting();
        	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}