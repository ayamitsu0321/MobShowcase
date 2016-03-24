package ayamitsu.mobshowcase;

import ayamitsu.mobshowcase.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
        modid = MobShowcase.MODID,
        name = MobShowcase.NAME,
        version = MobShowcase.VERSION
)
public class MobShowcase {

    public static final String MODID = "mobshowcase";
    public static final String NAME = "MobShowcase";
    public static final String VERSION = "1.1.0";

    @Mod.Instance(MODID)
    public static MobShowcase instance;

    @SidedProxy(clientSide = "ayamitsu.mobshowcase.client.ClientProxy", serverSide = "ayamitsu.mobshowcase.server.ServerProxy")
    public static AbstractProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Blocks.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, this.proxy.guiHandler);
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

}