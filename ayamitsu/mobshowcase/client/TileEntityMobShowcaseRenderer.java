package ayamitsu.mobshowcase.client;

import ayamitsu.mobshowcase.common.*;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class TileEntityMobShowcaseRenderer extends TileEntitySpecialRenderer
{
	public void setTileEntityRenderer(TileEntityRenderer par1TileEntityRenderer)
	{
		super.setTileEntityRenderer(par1TileEntityRenderer);
	}
	
	public void renderTileEntityMobShowcase(TileEntityMobShowcase showcase, double par2, double par4, double par6, float par8)
	{
		Entity entity = showcase.getEntity();
		showcase.setField_a(par8);
		
		if (entity == null)
		{
			return;
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2 + 0.5F, (float)par4, (float)par6 + 0.5F);
		RenderHelper.enableStandardItemLighting();
		float f1 = showcase.getMobScale();
    	float f2 = (float)showcase.getMobMagnification();
    	float f3 = MathHelper.sqrt_float(f1 * f1 + f2 * f2);
    	//sizeというか、scale
    	float scale = 1.0F * f1 * f2;
    	//補正
    	float advance = entity instanceof EntitySquid ? 0.6F : entity instanceof EntityGhast || entity instanceof EntityDragon ? 0.4F : 0.0F;
    	float rotation = (float)(showcase.yaw2 + (showcase.yaw - showcase.yaw2) * (double)par8) * 10F;
    	float f4 = showcase.isDoRotation() ? rotation : -rotation;
    	//回転
    	GL11.glRotatef(showcase.getMobRotationX() + f4, 0.0F, 1.0F, 0.0F);
    	//Entityによって配置がちょっとちがう
    	GL11.glTranslatef(0.0F, entity.yOffset + advance + 0.25F * (f3 - (f3 - 1F)), 0.0F);
    	GL11.glTranslatef(0.0F, 0.0F - (showcase.getMobTranslate() * f2 - 0.5F), 0.0F);
        GL11.glScalef(scale, scale, scale);//
    	//色
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glColor4f(1.0F * showcase.getMobColorRGB(0), 1.0F * showcase.getMobColorRGB(1), 1.0F * showcase.getMobColorRGB(2), 1.0F);
    	//描画みたいなの、Entityだす
    	entity.setLocationAndAngles(par2, par4 , par6, 0.0F, 0.0F);
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glPopMatrix();
    	RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntityMobShowcase((TileEntityMobShowcase)par1TileEntity, par2, par4, par6, par8);
	}
}