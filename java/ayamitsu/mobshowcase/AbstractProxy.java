package ayamitsu.mobshowcase;

import net.minecraftforge.fml.common.network.IGuiHandler;

public abstract class AbstractProxy {

    public IGuiHandler guiHandler = this.createGuiHandler();

    public void preInit() {
    }

    public void init() {
    }

    public void postInit() {
    }

    public abstract IGuiHandler createGuiHandler();

}