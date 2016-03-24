package ayamitsu.mobshowcase.client;

import ayamitsu.mobshowcase.AbstractProxy;
import ayamitsu.mobshowcase.MobShowcase;
import ayamitsu.mobshowcase.client.gui.GuiMobShowcase;
import ayamitsu.mobshowcase.client.renderer.tileentity.TileEntityMobShowcaseRenderer;
import ayamitsu.mobshowcase.init.Blocks;
import ayamitsu.mobshowcase.inventory.ContainerMobShowcase;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by ayamitsu0321 on 2016/03/22.
 */
public class ClientProxy extends AbstractProxy {

    @Override
    public void preInit() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.showcaseStone), 0, new ModelResourceLocation(new ResourceLocation(MobShowcase.MODID, "mob_showcase_stone"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Blocks.showcaseGlass), 0, new ModelResourceLocation(new ResourceLocation(MobShowcase.MODID, "mob_showcase_glass"), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMobShowcase.class, new TileEntityMobShowcaseRenderer());
    }

    @Override
    public IGuiHandler createGuiHandler() {
        return new IGuiHandler() {

            @Override
            public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity instanceof TileEntityMobShowcase) {
                    return new ContainerMobShowcase(player.inventory, (TileEntityMobShowcase)tileEntity);
                }

                return null;
            }

            @Override
            public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
                if (tileEntity instanceof TileEntityMobShowcase) {
                    return new GuiMobShowcase(player.inventory, (TileEntityMobShowcase)tileEntity);
                }

                return null;
            }

        };
    }

}
