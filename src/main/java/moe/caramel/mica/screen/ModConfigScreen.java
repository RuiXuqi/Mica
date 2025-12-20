package moe.caramel.mica.screen;

import moe.caramel.mica.ModConfig;
import moe.caramel.mica.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModConfigScreen extends GuiConfig {
    public ModConfigScreen(final GuiScreen parent) {
        super(parent, getRootConfigElements(), Reference.MOD_ID, false, false, Reference.MOD_NAME);
    }

    private static @Nonnull List<IConfigElement> getRootConfigElements() {
        final List<IConfigElement> elements = new ArrayList<>();
        Arrays.stream(ModConfig.CATEGORIES).forEach(
                category -> elements.add(new ConfigElement(ModConfig.getConfig().getCategory(category)))
        );
        return elements;
    }
}
