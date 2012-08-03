package net.minecraft.src.mobshowcase;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiButtonMob extends GuiButton
{
	public GuiButtonMob(int par1, int par2, int par3, String par4Str)
    {
    	super(par1, par2, par3, 10, 10, par4Str);
    }
	
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
        drawTexturedModalRect(xPosition, yPosition, 176 + i * 12, 24, width, height);
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

        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, j);
    }
}