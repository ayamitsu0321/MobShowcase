package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiSliderColor extends GuiButton
{
	protected TileEntityMobShowcase mobShowcase;
	private float sliderValue;
	public boolean dragging;
	
	public GuiSliderColor(TileEntityMobShowcase par1, int par2, int x, int y, String par5Str, float par6)
	{
		super(par2, x, y, 12, 32, par5Str);
		sliderValue = 1.0F;
		dragging = false;
		sliderValue = par6;//ƒ{ƒ^ƒ“‚Ì‰ŠúˆÊ’u
	}
	
	protected int getHoverState(boolean par1)
    {
        return 0;
    }
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        FontRenderer fontrenderer = par1Minecraft.fontRenderer;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/mobshowcase/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int i = getHoverState(flag);
        mouseDragged(par1Minecraft, par2, par3);
        int j = 0xe0e0e0;

        if (!enabled)
        {
            j = 0xffa0a0a0;
        }
        else if (flag)
        {
            j = 0xffffa0;
        }

        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 4) / 2, j);
    }
	
	protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        if (dragging)
        {
            sliderValue = (float)(par3 - (yPosition + 3)) / (float)(height - 6);

            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }

            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }

        }
    	
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(xPosition, yPosition + (int)(sliderValue * (float)(height - 6)), 188, 34, 12, 6);
    }
	
	public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            sliderValue = (float)(par3 - (yPosition + 3)) / (float)(height - 6);

            if (sliderValue < 0.0F)
            {
                sliderValue = 0.0F;
            }

            if (sliderValue > 1.0F)
            {
                sliderValue = 1.0F;
            }

        	dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }
	
	public void mouseReleased(int par1, int par2)
    {
        dragging = false;
    }
	
	public float getSliderValue()
	{
		return sliderValue;
	}
}