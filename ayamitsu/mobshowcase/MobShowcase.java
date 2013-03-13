package ayamitsu.mobshowcase;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import ayamitsu.mobshowcase.common.BlockMobShowcase;
import ayamitsu.mobshowcase.common.CommonProxy;
import ayamitsu.mobshowcase.common.MobReturnerEgg;
import ayamitsu.mobshowcase.common.MobReturnerSpawnEgg;
import ayamitsu.mobshowcase.common.MobShowcaseRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
	modid = "MobShowcase",
	name = "MobShowcase",
	version = "1.0.0"
)
@NetworkMod(
	clientSideRequired = true,
	serverSideRequired = false,
	channels = "mobshowcase",
	packetHandler = ayamitsu.mobshowcase.common.PacketHandler.class
)
public class MobShowcase
{
	@Mod.Instance("MobShowcase")
	public static MobShowcase instance;

	@SidedProxy(clientSide = "ayamitsu.mobshowcase.client.ClientProxy", serverSide = "ayamitsu.mobshowcase.common.CommonProxy")
	public static CommonProxy proxy;

	public static Block showcase_stone;
	public static Block showcase_glass;
	public static int showcase_stoneId;
	public static int showcase_glassId;
	public static int renderId;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		try
		{
			config.load();
			int def_1 = 201;
			int def_2 = 202;
			Property prop1 = config.getBlock("showcase_stone", def_1);
			Property prop2 = config.getBlock("showcase_glass", def_2);
			this.showcase_stoneId = prop1.getInt(def_1);
			this.showcase_glassId = prop2.getInt(def_2);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Massage");
		}
		finally
		{
			config.save();
		}
	}

	@Mod.Init
	public void init(FMLInitializationEvent event)
	{
		this.renderId = proxy.getUniqueRenderId();
		this.showcase_stone = new BlockMobShowcase(this.showcase_stoneId, Material.rock, renderId);
		this.showcase_stone.setHardness(1.0F).setUnlocalizedName("mobShowcase.stone").setCreativeTab(CreativeTabs.tabDecorations);
		this.showcase_glass = new BlockMobShowcase(this.showcase_glassId, Material.glass, 0);
		this.showcase_glass.setHardness(0.3F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("mobShowcase.glass").setCreativeTab(CreativeTabs.tabDecorations);
		GameRegistry.registerBlock(this.showcase_stone, "mobShowcase.stone");
		GameRegistry.registerBlock(this.showcase_glass,  "mobShowcase.glass");
		LanguageRegistry.instance().addNameForObject(this.showcase_stone, "en_US", "Showcase Stone");
		LanguageRegistry.instance().addNameForObject(this.showcase_glass, "en_US", "Showcase Glass");
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);

		GameRegistry.addRecipe(new ItemStack(this.showcase_stone.blockID, 2, 0),
			new Object[] {
				"XXX",
				"XYX",
				'X', Block.stone,
				'Y', new ItemStack(Item.dyePowder, 1, 4)
			}
		);

		GameRegistry.addRecipe(new ItemStack(this.showcase_glass.blockID, 2, 0),
			new Object[] {
				"XXX",
				"XYX",
				'X', Block.glass,
				'Y', new ItemStack(Item.dyePowder, 1, 4)
			}
		);

		this.proxy.load();
		MobShowcaseRegistry.addReturner(Item.monsterPlacer.itemID, new MobReturnerSpawnEgg());
		MobShowcaseRegistry.addReturner(Item.egg.itemID, new MobReturnerEgg());
		MobShowcaseRegistry.addReturner(Block.dragonEgg.blockID, new MobReturnerEgg());
	}
}