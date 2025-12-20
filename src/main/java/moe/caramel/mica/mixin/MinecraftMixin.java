package moe.caramel.mica.mixin;

import com.sun.jna.Platform;
import moe.caramel.mica.natives.DwmApi;
import moe.caramel.mica.natives.NtDll;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class MinecraftMixin {

    @Shadow
    private boolean fullscreen;

    @Inject(method = "createDisplay", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        // Check OS
        if (Platform.getOSType() != Platform.WINDOWS) {
            return;
        }

        // Initialize Mica
        NtDll.getBuildNumber();
        DwmApi.updateDwm(this.fullscreen, Display.getWindow());
    }

    @Inject(method = "toggleFullscreen", at = @At("TAIL"))
    private void updateFullscreen(CallbackInfo ci) {
        // Check OS
        if (Platform.getOSType() != Platform.WINDOWS) {
            return;
        }

        // Update DWM
        DwmApi.updateDwm(this.fullscreen, Display.getWindow());
    }
}
