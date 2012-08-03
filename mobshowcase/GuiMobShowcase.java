package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiMobShowcase extends GuiContainer
{
	protected TileEntityMobShowcase mobShowcase;
	protected GuiSliderHor sliderHor;
	protected GuiSliderVer sliderVer;
	protected GuiButton buttonX;
	protected GuiButton buttonScale;
	protected GuiSliderColor sliderR;
	protected GuiSliderColor sliderG;
	protected GuiSliderColor sliderB;
	protected GuiButton buttonRotate;
	protected GuiSliderColor sliderDelay;
	
	public GuiMobShowcase(InventoryPlayer par1, TileEntityMobShowcase par2)
	{
		super(new ContainerMobShowcase(par1, par2));
		mobShowcase = par2;
	}
	
	public void initGui()
	{
		super.initGui();
		controlList.clear();
		int defID = 10;
		//rotate
		buttonX = new GuiButtonMob(defID + 1, width / 2 - 8, height / 2 - 76, ">");
		controlList.add(buttonX);
		//magnification
		buttonScale =  new GuiButtonMob(defID + 4, width / 2 - 8, height / 2 - 63, "*");
		controlList.add(buttonScale);
		//scale
		sliderHor = new GuiSliderHor(mobShowcase, defID + 5, width / 2 + 9, height / 2 - 64, "Scale", mobShowcase.getMobScale() / 2.0F);
		controlList.add(sliderHor);
		//translate
		sliderVer = new GuiSliderVer(mobShowcase, defID + 6, width / 2 - 83, height / 2 - 76, "Y", mobShowcase.getMobTranslate());
		controlList.add(sliderVer);
		//color
		sliderR = new GuiSliderColor(mobShowcase, defID + 7, width / 2 - 10, height / 2 - 36, "R", mobShowcase.getMobColorRGB(0));
		sliderG = new GuiSliderColor(mobShowcase, defID + 8, width / 2 + 4, height / 2 - 36, "G", mobShowcase.getMobColorRGB(1));
		sliderB = new GuiSliderColor(mobShowcase, defID + 9, width / 2 + 17, height / 2 - 36, "B", mobShowcase.getMobColorRGB(2));
		controlList.add(sliderR);
		controlList.add(sliderG);
		controlList.add(sliderB);
		//do rotate
		buttonRotate = new GuiButtonMob(defID + 10, width / 2 + 30, height / 2 - 48, "›");
		controlList.add(buttonRotate);
		sliderDelay = new GuiSliderColor(mobShowcase, defID + 7, width / 2 + 30, height / 2 - 36, "›", mobShowcase.getDelay());
		controlList.add(sliderDelay);
	}
	
	protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal("Showcase"), 106, 6, 0x404040);
    }
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int i = mc.renderEngine.getTexture("/mobshowcase/gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
    	//System.out.println("A");
    	//‚±‚±‚©‚çmob‚Ì•\Ž¦
    	//TileEntity‚©‚ç‹¤—L‚µ‚Ä‚éEntity‚Ìî•ñŽæ“¾
    	Entity entity = mobShowcase.getEntity();
		
		if (entity != null)
        {
        	//System.out.println("TRUE");
        	GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    		GL11.glPushMatrix();
    		GL11.glTranslatef(j + 46, k + 75, 50F);
        	float defScale = 60F;
        	float scale = defScale * mobShowcase.getMobScale() * mobShowcase.getMobMagnification();
            GL11.glScalef(-scale, scale, scale);
        	GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        	//‰ñ“]
        	GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        	RenderHelper.enableStandardItemLighting();
        	float rotation = (float)(mobShowcase.yaw2 + (mobShowcase.yaw - mobShowcase.yaw2) * (double)mobShowcase.getField_a()) * 10F;
        	float f4 = mobShowcase.isDoRotation() ? rotation : -rotation;
        	GL11.glRotatef(mobShowcase.getMobRotationX() - 135F + f4, 0.0F, 1.0F, 0.0F);
        	float advance = entity instanceof EntitySquid ? 1.2F : entity instanceof EntityGhast || entity instanceof EntityDragon ? 2.8F : 0.0F;
        	GL11.glTranslatef(0.0F, entity.yOffset + advance, 0.0F);
        	//F
        	GL11.glColor4f(1.0F * mobShowcase.getMobColorRGB(0), 1.0F * mobShowcase.getMobColorRGB(1), 1.0F * mobShowcase.getMobColorRGB(2), 1.0F);
        	RenderManager.instance.playerViewY = 180F;
        	//Entity•`‰æ‚Ì–{‘Ì
            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        	GL11.glPopMatrix();
    		RenderHelper.disableStandardItemLighting();
        	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
    }
	
	/*public boolean doesGuiPauseGame()
    {
        return true;
    }*/

	//ƒ{ƒ^ƒ“‚¨‚·‚Æ‚«‚Ì
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (!par1GuiButton.enabled)
        {
            return;
        }
		
		if (par1GuiButton.id == buttonX.id)
		{//‰ñ“]
			mobShowcase.setMobRotationX(mobShowcase.getMobRotationX() + 45F);
		}
		else if (par1GuiButton.id == buttonScale.id)
		{
			int i = mobShowcase.getMobMagnification();
			switch (i) {
				case 1: mobShowcase.setMobMagnification(2); break;
				case 2: mobShowcase.setMobMagnification(3); break;
				case 3: mobShowcase.setMobMagnification(4); break;
				case 4: mobShowcase.setMobMagnification(1); break;
				default: mobShowcase.setMobMagnification(1); break;
			}
		}
		else if (par1GuiButton.id == buttonRotate.id)
		{
			if (mobShowcase.isDoRotation())
			{
				mobShowcase.setDoRotation(false);
				//mobShowcase.yaw = 0.0D;
			}
			else
			{
				mobShowcase.setDoRotation(true);
			}
		}
	}

	public void updateScreen()
	{
		super.updateScreen();
		mobShowcase.setMobTranslate(sliderVer.getSliderValue());
		mobShowcase.setMobScale(sliderHor.getSliderValue() * 2.0F);
		mobShowcase.setMobColorRGB(sliderR.getSliderValue(), 0);
		mobShowcase.setMobColorRGB(sliderG.getSliderValue(), 1);
		mobShowcase.setMobColorRGB(sliderB.getSliderValue(), 2);
		mobShowcase.setDelay(sliderDelay.getSliderValue());
		
		mobShowcase.yaw = mobShowcase.getDelay() > 0.0F ? mobShowcase.yaw : 0.0D;
		
		if (mobShowcase.getMobRotationX() >= 360F && !mobShowcase.isDoRotation())
		{
			mobShowcase.setMobRotationX(0F);
		}
	}
}