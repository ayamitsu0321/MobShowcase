package ayamitsu.mobshowcase.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiButtonShowcase extends GuiButton
{
	public GuiButtonShowcase(int par1, int par2, int par3, String display)
    {
    	super(par1, par2, par3, 10, 10, display);
    }

	@Override
	public void drawButton(Minecraft mc, int par2, int par3)
	{
		if (!this.drawButton)
		{
			return;
		}

		FontRenderer fontrenderer = mc.fontRenderer;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/ayamitsu/mobshowcase/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
		int i = this.getHoverState(flag);
		this.drawTexturedModalRect(this.xPosition, this.yPosition, 176 + i * 12, 24, this.width, this.height);
		this.mouseDragged(mc, par2, par3);
		int j = 0xe0e0e0;

		if (!this.enabled)
		{
			j = 0xffa0a0a0;
		}
		else if (flag)
		{
			j = 0xffffa0;
		}

		this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
	}
}