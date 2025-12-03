package fr.noahboos.essor.client.ui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class EssorEquipmentScreen extends Screen {
    private int panelWidth;
    private int panelHeight;
    private int panelLeft;
    private int panelTop;

    public EssorEquipmentScreen() {
        super(Component.literal("Essor - Equipment panel"));
    }

    @Override
    protected void init() {
        this.panelWidth = (int) Math.round(this.width * 0.6f);
        this.panelHeight = (int) Math.round(this.height * 0.6f);
        this.panelLeft = (this.width - this.panelWidth) / 2;
        this.panelTop = (this.height - this.panelHeight) / 2;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(this.panelLeft, this.panelTop, this.panelLeft + this.panelWidth, this.panelTop + this.panelHeight, 0xFF202020);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);

        graphics.drawString(
                this.font,
                "Essor - Equipment panel",
                this.panelLeft + this.panelWidth / 2 - this.font.width("Essor - Equipment panel") / 2,
                this.panelTop + 48,
                0xFFFFE01B
        );

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}