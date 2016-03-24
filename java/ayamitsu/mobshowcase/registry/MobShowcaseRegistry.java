package ayamitsu.mobshowcase.registry;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;
import java.util.List;

/**
 * Created by ayamitsu0321 on 2016/03/24.
 */
public final class MobShowcaseRegistry {

    public static interface IMobContainer {

        boolean accept(ItemStack is);

        Entity getEntity(ItemStack is, World world);

    }

    private static List<IMobContainer> mobContainerList = Lists.newArrayList();

    public static void registerMobContainer(IMobContainer mobContainer) {
        mobContainerList.add(mobContainer);
    }

    public static boolean contains(ItemStack is) {
        return mobContainerList.stream().anyMatch(o -> o.accept(is));
    }

    public static Entity getEntity(ItemStack is) {
        IMobContainer mobContainer = mobContainerList.stream().filter(o -> o.accept(is)).findAny().orElse(null);

        if (mobContainer != null) {
            return mobContainer.getEntity(is, dummyWorld);
        } else {

            return null;
        }
    }

    public static final BlockPos dummyBlockPos = new BlockPos(0, 0, 0);

    /**
     * for rendering entity
     */
    public static final World dummyWorld = new World(
            new ISaveHandler() {

                @Override
                public WorldInfo loadWorldInfo() {
                    return null;
                }

                @Override
                public void checkSessionLock() throws MinecraftException {}

                @Override
                public IChunkLoader getChunkLoader(WorldProvider provider) {
                    return null;
                }

                @Override
                public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}

                @Override
                public void saveWorldInfo(WorldInfo worldInformation) {}

                @Override
                public IPlayerFileData getPlayerNBTManager() {
                    return null;
                }

                @Override
                public void flush() {}

                @Override
                public File getWorldDirectory() {
                    return null;
                }

                @Override
                public File getMapFileFromName(String mapName) {
                    return null;
                }

                @Override
                public TemplateManager getStructureTemplateManager() {
                    return null;
                }

            },
            new WorldInfo(new WorldSettings(0L, WorldSettings.GameType.NOT_SET, false, false, WorldType.DEFAULT), "Dummy"),
            new WorldProviderSurface(),
            new Profiler(),
            true) {

        @Override
        protected IChunkProvider createChunkProvider() {
            return null;
        }

        @Override
        protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
            return false;
        }

    };


    static {
        dummyWorld.provider.registerWorld(dummyWorld);

        // egg -> chicken
        registerMobContainer(new IMobContainer() {

            @Override
            public boolean accept(ItemStack is) {
                return is.getItem() == Items.egg;
            }

            @Override
            public Entity getEntity(ItemStack is, World world) {
                return new EntityChicken(world);
            }

        });

        // spawn_egg -> any entity
        registerMobContainer(new IMobContainer() {

            @Override
            public boolean accept(ItemStack is) {
                if (is.getItem() != Items.spawn_egg) return false;

                String name = ItemMonsterPlacer.getEntityIdFromItem(is);
                return name != null && EntityList.isStringValidEntityName(name);
            }

            @Override
            public Entity getEntity(ItemStack is, World world) {
                String name = ItemMonsterPlacer.getEntityIdFromItem(is);
                Entity entity = EntityList.func_188429_b(name, world);

                if (entity instanceof EntitySkeleton)
                    ((EntitySkeleton)entity).onInitialSpawn(world.getDifficultyForLocation(dummyBlockPos), null);

                return entity;
            }

        });

        // dragon_egg -> dragon
        registerMobContainer(new IMobContainer() {

            @Override
            public boolean accept(ItemStack is) {
                return is.getItem() instanceof ItemBlock && Block.getBlockFromItem(is.getItem()) == Blocks.dragon_egg;
            }

            @Override
            public Entity getEntity(ItemStack is, World world) {
                return new EntityDragon(world);
            }

        });
    }



}
