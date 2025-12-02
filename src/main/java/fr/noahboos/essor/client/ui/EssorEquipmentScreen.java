package fr.noahboos.essor.client.ui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class EssorEquipmentScreen extends Screen {
    public EssorEquipmentScreen() {
        super(Component.literal("Essor - Equipment panel"));
    }

    @Override
    protected void init() {

    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(0, 0, this.width, this.height, 0xFF202020);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);

        graphics.drawString(
                this.font,
                "Essor - Equipment panel",
                this.width / 2 - this.font.width("Essor - Equipment panel") / 2,
                20,
                0xFFFFE01B
        );

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}