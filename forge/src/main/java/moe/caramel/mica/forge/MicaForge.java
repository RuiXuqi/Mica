package moe.caramel.mica.forge;

import moe.caramel.mica.Mica;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mica.MOD_ID)
public final class MicaForge {

    public MicaForge(final FMLJavaModLoadingContext context) {
        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(e -> commonSetup(context));
    }

    private void commonSetup(final FMLJavaModLoadingContext context) {
        context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> {
            return new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> Mica.configScreen(screen));
        });
    }
}
