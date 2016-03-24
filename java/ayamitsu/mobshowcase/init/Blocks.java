package ayamitsu.mobshowcase.init;

import ayamitsu.mobshowcase.MobShowcase;
import ayamitsu.mobshowcase.block.BlockMobShowcase;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;


/**
 * Created by ayamitsu0321 on 2016/03/22.
 */
public class Blocks {

    public static final CreativeTabs tabMobShowcase = new CreativeTabs(MobShowcase.MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.showcaseStone);
        }
    };

    public static final Block showcaseStone;
    public static final Block showcaseGlass;


    public static void init() {
        // blocks
        registerBlock("mob_showcase_stone", showcaseStone);
        registerBlock("mob_showcase_glass", showcaseGlass);

        // recipies
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(showcaseStone)),
                "XXX",
                "XYX",
                'X', net.minecraft.init.Blocks.stone,
                'Y', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage())
        );

        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(showcaseGlass)),
                "XXX",
                "XYX",
                'X', net.minecraft.init.Blocks.glass,
                'Y', new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage())
        );
    }

    static {
        showcaseStone = new BlockMobShowcase(Material.rock).setBoundingBox(new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.25D, 0.9D)).setRegistryName(MobShowcase.MODID, "mob_showcase_stone").setUnlocalizedName(MobShowcase.MODID + ".mob_showcase_stone").setCreativeTab(tabMobShowcase);
        showcaseGlass = new BlockMobShowcase(Material.glass).setRegistryName(MobShowcase.MODID, "mob_showcase_glass").setUnlocalizedName(MobShowcase.MODID + ".mob_showcase_glass").setCreativeTab(tabMobShowcase).setLightOpacity(15);

        GameRegistry.registerTileEntity(TileEntityMobShowcase.class, "mob_showcase");
    }

    private static void registerBlock(String name, Block block) {
        GameRegistry.registerBlock(block, name);
    }

}
