package ayamitsu.mobshowcase.client;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ayamitsu.mobshowcase.common.ContainerMobShowcase;
import ayamitsu.mobshowcase.common.TileEntityMobShowcase;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiMobShowcase extends GuiContainer
{
	public TileEntityMobShowcase showcase;

	public GuiMobShowcase(EntityPlayer player, World world, int blockX, int blockY, int blockZ)
	{
		super(new ContainerMobShowcase(player, world, blockX, blockY, blockZ));
		this.showcase = (TileEntityMobShowcase)world.getBlockTileEntity(blockX, blockY, blockZ);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		this.buttonList.clear();
		this.buttonList.add(new GuiButtonShowcase(0, this.width / 2 - 8, this.height / 2 - 76, ">"));
		this.buttonList.add(new GuiButtonShowcase(1, this.width / 2 - 8, this.height / 2 - 63, "*"));
		this.buttonList.add(new GuiButtonShowcase(2, this.width / 2 + 30, this.height / 2 - 48, "○"));
		this.buttonList.add(new GuiSliderHorizon(3, this.width / 2 + 9, this.height / 2 - 64, "Scale", this.showcase.getMobScale() / 2.0F));
		this.buttonList.add(new GuiSliderVertical(4, this.width / 2 - 83, this.height / 2 - 76, "Y", this.showcase.getMobTranslate()));
		this.buttonList.add(new GuiSliderColor(5, this.width / 2 - 10, this.height / 2 - 36, "R", this.showcase.getMobColorRGB(0)));
		this.buttonList.add(new GuiSliderColor(6, this.width / 2 + 4, this.height / 2 - 36, "G", this.showcase.getMobColorRGB(1)));
		this.buttonList.add(new GuiSliderColor(7, this.width / 2 + 17, this.height / 2 - 36, "B", this.showcase.getMobColorRGB(2)));
		this.buttonList.add(new GuiSliderColor(8, this.width / 2 + 30, this.height / 2 - 36, "○", this.showcase.getDelay()));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("Showcase"), 106, 6, 0x404040);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		//this.mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/ayamitsu/mobshowcase/gui.png"));
		this.mc.renderEngine.func_98187_b("/ayamitsu/mobshowcase/gui.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
		Entity entity = this.showcase.getEntity();

		if (entity != null)
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        	GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glPushMatrix();
			GL11.glTranslatef(j + 46, k + 75, 50F);
			float defScale = 60F;
        	float scale = defScale * this.showcase.getMobScale() * this.showcase.getMobMagnification();
            GL11.glScalef(-scale, scale, scale);
        	GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        	//・ｽ・ｽ]
        	GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        	//RenderHelper.enableStandardItemLighting();
        	float rotation = (float)(this.showcase.yaw2 + (this.showcase.yaw - this.showcase.yaw2) * (double)this.showcase.getField_a()) * 10F;
        	float f4 = this.showcase.isDoRotation() ? rotation : -rotation;
        	GL11.glRotatef(this.showcase.getMobRotationX() - 135F + f4, 0.0F, 1.0F, 0.0F);
        	float advance = entity instanceof EntitySquid ? 1.2F : entity instanceof EntityGhast || entity instanceof EntityDragon ? 2.8F : 0.0F;
        	GL11.glTranslatef(0.0F, entity.yOffset + advance, 0.0F);
        	//・ｽF
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	GL11.glColor4f(1.0F * this.showcase.getMobColorRGB(0), 1.0F * this.showcase.getMobColorRGB(1), 1.0F * this.showcase.getMobColorRGB(2), 1.0F);
        	RenderManager.instance.playerViewY = 180F;
        	//Entity・ｽ`・ｽ・ｽﾌ本・ｽ・ｽ
            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        	GL11.glPopMatrix();
    		//RenderHelper.disableStandardItemLighting();
        	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}


		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	protected void mouseMovedOrUp(int par1, int par2, int par3)
	{
		super.mouseMovedOrUp(par1, par2, par3);

		// GuiContainer・ｽ・ｽ・ｽI・ｽ[・ｽo・ｽ[・ｽ・ｽ・ｽC・ｽh・ｽ・ｽ・ｽﾄゑｿｽ・ｽﾌ擾ｿｽ・ｽ・ｽ・ｽ・ｽ・ｽ・ｽ・ｽ・ｽ・ｽﾄゑｿｽ・ｽﾌゑｿｽ!!
		try
		{
			GuiButton selectedButton = (GuiButton)ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, this, 8);

			if (selectedButton != null && par3 == 0)
			{
				selectedButton.mouseReleased(par1, par2);
				ObfuscationReflectionHelper.setPrivateValue(GuiScreen.class, this, (Object)null, 8);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		/*if (this.selectedButton != null && par3 == 0)
		{
			this.selectedButton.mouseReleased(par1, par2);
			this.selectedButton = null;
		}*/
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (!guibutton.enabled)
        {
            return;
        }


		if (guibutton.id == 0)
		{
			this.showcase.setMobRotationX(this.showcase.getMobRotationX() + 45F);
		}
		else if (guibutton.id == 1)
		{
			int i = this.showcase.getMobMagnification();// 1 <= i <= 3
			this.showcase.setMobMagnification((i < 4 && i >= 1 ? i + 1 : 1));
		}
		else if (guibutton.id == 2)
		{
			this.showcase.setDoRotation(!this.showcase.isDoRotation());
		}

		try
		{
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			this.showcase.writeToNBT(nbttagcompound);
			byte[] data = CompressedStreamTools.compress(nbttagcompound);
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("mobshowcase", data));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		this.showcase.setMobScale(((GuiSliderHorizon)this.buttonList.get(3)).getSliderValue() * 2.0F);
		this.showcase.setMobTranslate(((GuiSliderVertical)this.buttonList.get(4)).getSliderValue());

		this.showcase.setMobColorRGB(((GuiSliderColor)this.buttonList.get(5)).getSliderValue(), 0);
		this.showcase.setMobColorRGB(((GuiSliderColor)this.buttonList.get(6)).getSliderValue(), 1);
		this.showcase.setMobColorRGB(((GuiSliderColor)this.buttonList.get(7)).getSliderValue(), 2);
		this.showcase.setDelay(((GuiSliderColor)this.buttonList.get(8)).getSliderValue());
		this.showcase.yaw = this.showcase.getDelay() > 0.0F ? this.showcase.yaw : 0.0D;

		if (this.showcase.getMobRotationX() >= 360F && !this.showcase.isDoRotation())
		{
			this.showcase.setMobRotationX(0F);
		}
	}

	/**
	 * ・ｽﾂゑｿｽ・ｽ・ｽ・ｽﾆゑｿｽ・ｽﾉ一括・ｽ・ｽ・ｽ・ｽPacket・ｽ・ｽ・ｽM
	 */
	@Override
	public void onGuiClosed()
	{
		try
		{
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			this.showcase.writeToNBT(nbttagcompound);
			byte[] data = CompressedStreamTools.compress(nbttagcompound);
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("mobshowcase", data));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}