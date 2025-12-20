package moe.caramel.mica;

import moe.caramel.mica.screen.AlertScreen;
import moe.caramel.mica.screen.ModConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

@SuppressWarnings("unused")
public class ModConfigGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraft) {
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    /**
     * Create a config screen.
     *
     * @param screen previous screen
     * @return config screen
     */
    @Override
    public GuiScreen createConfigGui(final GuiScreen screen) {
        // Check OS compatibility
        if (!Mica.checkCompatibility()) {
            return new AlertScreen(
                    () -> Minecraft.getMinecraft().displayGuiScreen(screen),
                    TextFormatting.RED.toString() + TextFormatting.BOLD + I18n.format("mica.unsupported_os.title"),
                    I18n.format("mica.unsupported_os.description", Reference.MOD_NAME)
            );
        }
        return new ModConfigScreen(screen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Set.of();
    }
}
