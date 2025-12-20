package moe.caramel.mica;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Mica Instance
 */
@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        guiFactory = "moe.caramel.mica.ModConfigGuiFactory"
)
public final class Mica {
    public static final int MINIMUM_BUILD_NUM = 22000;
    public static final int BACKDROP_BUILD_NUM = 22621;

    public static int majorVersion = Integer.MIN_VALUE;
    public static int buildNumber = Integer.MIN_VALUE;

    @Mod.EventHandler
    public void preInit(@Nonnull FMLPreInitializationEvent event) {
        ModConfig.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }

    /**
     * Check compatibility.
     *
     * @return if {@code true}, it is compatible.
     */
    public static boolean checkCompatibility() {
        return (Mica.majorVersion >= 10 && Mica.buildNumber >= Mica.MINIMUM_BUILD_NUM);
    }
}
