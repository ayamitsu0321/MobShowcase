package ayamitsu.mobshowcase.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

@cpw.mods.fml.relauncher.SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
public class GuiSliderHorizon extends GuiButton
{
	private float sliderValue;
	public boolean dragging;

	public GuiSliderHorizon(int i, int x, int y, String display, float f)
	{
		super(i, x, y, 72, 12, display);
		this.sliderValue = f;
		this.dragging = false;
	}

	@Override
	protected int getHoverState(boolean par1)
	{
		return 0;
	}

	@Override
	public void drawButton(Minecraft mc, int par2, int par3)
	{
		if (!this.drawButton)
		{
			return;
		}

		FontRenderer fontrenderer = mc.fontRenderer;
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/ayamitsu/mobshowcase/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.func_98187_b("/ayamitsu/mobshowcase/gui.png");
		boolean flag = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
		int i = this.getHoverState(flag);
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

	@Override
	protected void mouseDragged(Minecraft mc, int par2, int par3)
	{
		if (!this.drawButton)
		{
			return;
		}

		if (this.dragging)
		{
			this.sliderValue = (float)(par2 - (this.xPosition + 3)) / (float)(this.width - 6);

			if (this.sliderValue < 0.0F)
			{
				this.sliderValue = 0.0F;
			}

			if (this.sliderValue > 1.0F)
			{
				this.sliderValue = 1.0F;
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(this.xPosition  + (int)(this.sliderValue * (float)(this.width - 6)), this.yPosition, 188, 12, 6, 12);
	}

	@Override
	public boolean mousePressed(Minecraft mc, int par2, int par3)
	{
		if (super.mousePressed(mc, par2, par3))
		{
			this.sliderValue = (float)(par2 - (this.xPosition + 3)) / (float)(this.width - 6);

			if (this.sliderValue < 0.0F)
			{
				this.sliderValue = 0.0F;
			}

			if (this.sliderValue > 1.0F)
			{
				this.sliderValue = 1.0F;
			}

			this.dragging = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void mouseReleased(int par1, int par2)
	{
		this.dragging = false;
	}

	public float getSliderValue()
	{
		return this.sliderValue;
	}
}