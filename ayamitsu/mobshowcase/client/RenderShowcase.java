package ayamitsu.mobshowcase.client;

import net.minecraft.src.*;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderShowcase implements ISimpleBlockRenderingHandler
{
	int renderId;
	
	public RenderShowcase(int id)
	{
		this.renderId = id;
	}
	
	@Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderblocks)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if (modelId == this.renderId)
		{
			block.setBlockBoundsForItemRender();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
    		tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, metadata));
            tessellator.draw();
    		tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -0.9F);
            renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 0.9F);
            renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-0.9F, 0.0F, 0.0F);
            renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, metadata));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.9F, 0.0F, 0.0F);
            renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, metadata));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}
	
	@Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderblocks)
	{
		if (modelId == this.renderId)
		{
			block.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
			renderblocks.renderStandardBlock(block, x, y, z);
			block.setBlockBoundsForItemRender();
			return true;
		}
		
		return false;
	}
	
	@Override
    public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
    public int getRenderId()
	{
		return this.renderId;
	}
}