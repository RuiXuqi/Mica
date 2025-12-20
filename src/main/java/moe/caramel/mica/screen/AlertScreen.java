package moe.caramel.mica.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class AlertScreen extends GuiScreen {
    private final Runnable callback;
    private final String title;
    private final String messageText;
    private List<String> message;

    public AlertScreen(@Nonnull Runnable callback, String title, String messageText) {
        this.callback = callback;
        this.title = title;
        this.messageText = messageText;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.message = this.fontRenderer.listFormattedStringToWidth(this.messageText, this.width - 50);
        int i = this.message.size() * 9;
        int j = MathHelper.clamp(90 + i + 12, this.height / 6 + 96, this.height - 24);
        this.addButton(new GuiButton(0, (this.width - 150) / 2, j, 150, 20, I18n.format("mica.gui.ok")));
    }

    @Override
    protected void actionPerformed(@Nonnull final GuiButton button) {
        if (button.id == 0) this.callback.run();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        int x = this.width / 2;
        this.drawCenteredString(this.fontRenderer, this.title, x, 70, -1);

        int current = 90;
        for (String line : this.message) {
            this.drawCenteredString(this.fontRenderer, line, x, current, -1);
            current += this.fontRenderer.FONT_HEIGHT + 1;
        }
    }
}
