package ayamitsu.mobshowcase.server;

import ayamitsu.mobshowcase.AbstractProxy;
import ayamitsu.mobshowcase.inventory.ContainerMobShowcase;
import ayamitsu.mobshowcase.tileentity.TileEntityMobShowcase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by ayamitsu0321 on 2016/03/22.
 */
public class ServerProxy extends AbstractProxy {

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
                return null;
            }

        };
    }
}
