package net.minecraft.src;

import net.minecraft.src.mobshowcase.*;
import org.lwjgl.opengl.GL11;

public class mod_MobShowcase extends BaseMod
{
	private final MobShowcaseList msList = MobShowcaseList.getInstance();
	public static Block mobShowcase;
	@MLProp(info = "stone id")
	public static int mobShowcaseID = 201;
	public static Block mobShowcase2;
	@MLProp(info = "glass id")
	public static int mobShowcase2ID = 202;
	public static int stageRender;
	public static boolean existMod;
	//
	boolean isDebug = false;
	
	public String getVersion()
	{
		return "v0.1.4";
	}
	
	@Override
	public void load()
	{
		registerMS();
		stageRender = ModLoader.getUniqueBlockModelID(this, true);
		mobShowcase = new BlockMobShowcase(mobShowcaseID, Block.stone.blockIndexInTexture, Material.rock, stageRender);
		mobShowcase.setHardness(1.0F).setBlockName("Showcase Stone");
		mobShowcase2 = new BlockMobShowcase(mobShowcase2ID, Block.glass.blockIndexInTexture, Material.glass, 0);
		mobShowcase2.setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setBlockName("Showcase Glass");
		ModLoader.addName(mobShowcase, "Showcase Stone");
		ModLoader.addName(mobShowcase2, "Showcase Glass");
		ModLoader.registerBlock(mobShowcase);
		ModLoader.registerBlock(mobShowcase2);
		ModLoader.registerTileEntity(net.minecraft.src.mobshowcase.TileEntityMobShowcase.class, "Showcase", new TileEntityMobShowcaseRenderer());
		ModLoader.addRecipe(new ItemStack(mobShowcase, 2),
			new Object[]{
				//"",
				"XXX",
				"XYX",
				Character.valueOf('X'),Block.stone,
				Character.valueOf('Y'),new ItemStack(Item.dyePowder, 1, 4)
			});
		ModLoader.addRecipe(new ItemStack(mobShowcase2, 2),
			new Object[]{
				//"",
				"XXX",
				"XYX",
				Character.valueOf('X'),Block.glass,
				Character.valueOf('Y'),new ItemStack(Item.dyePowder, 1, 4)
			});
		
		if (isDebug)
		{//dragon's spawn egg and etc...
			ModLoader.registerEntityID(EntityDragon.class, "dragon", ModLoader.getUniqueEntityId(), 0xffffff, 0xffffff);
			ModLoader.registerEntityID(EntityBoat.class, "boat", ModLoader.getUniqueEntityId(), 0xffffff, 0xffffff);
		}
	}
	
	//ItemèÛë‘ÇÃBlockÇÃï\é¶
	@Override
	public void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
    	Tessellator tessellator = Tessellator.instance;
    	
    	if (block.getRenderType() == stageRender)
    	{
    		block.setBlockBoundsForItemRender();
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
    		tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
            tessellator.draw();
    		tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -0.9F);
            renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 0.9F);
            renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-0.9F, 0.0F, 0.0F);
            renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.9F, 0.0F, 0.0F);
            renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    	}
    }
	
	//WorldÇ≈ÇÃBlockÇÃï\é¶
	@Override
	public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
	{
		if (l == stageRender)
		{
			block.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
			renderblocks.renderStandardBlock(block, i, j, k);
			block.setBlockBoundsForItemRender();
        	return true;
		}
		return false;
	}
	
	@Override
	public void modsLoaded()
	{
		existMod = ModLoader.isModLoaded("mod_PokeloliFigure");
		//System.out.println("Pokeloli " + existMod);
	}
	
	private void registerMS()
	{
		msList.addMSMapping(net.minecraft.src.EntityChicken.class, Item.egg.shiftedIndex, 0);
		msList.addMSMapping(net.minecraft.src.EntityDragon.class, Block.dragonEgg.blockID, 0);
	}
}