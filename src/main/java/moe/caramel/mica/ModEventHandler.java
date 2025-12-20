package moe.caramel.mica;

import moe.caramel.mica.natives.DwmApi;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;

import javax.annotation.Nonnull;

public class ModEventHandler {
    @SubscribeEvent
    public void onConfigChanged(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ModConfig.syncConfig();
            DwmApi.updateDwm(Minecraft.getMinecraft().isFullScreen(), Display.getWindow());
        }
    }
}
