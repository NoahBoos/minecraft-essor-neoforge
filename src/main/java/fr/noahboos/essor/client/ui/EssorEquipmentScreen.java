package fr.noahboos.essor.client.ui;

import fr.noahboos.essor.Essor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EssorEquipmentScreen extends Screen {
    private int panelWidth;
    private int panelHeight;
    private int panelLeft;
    private int panelTop;
    private static final ResourceLocation PANEL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Essor.MODID, "textures/gui/container/equipment_panel.png");

    public EssorEquipmentScreen() {
        super(Component.literal("Essor - Equipment panel"));
    }

    @Override
    protected void init() {
        this.panelWidth = 384;
        this.panelHeight = 192;
        this.panelLeft = (this.width - this.panelWidth) / 2;
        this.panelTop = (this.height - this.panelHeight) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        this.renderTitles(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(0, 0, this.width, this.height, 0x91101010);
        graphics.blit(RenderPipelines.GUI_TEXTURED, PANEL_TEXTURE, this.panelLeft, this.panelTop, 0, 0, this.panelWidth, this.panelHeight, 384, 192);
    }

    public void renderTitles(GuiGraphics graphics) {
        graphics.drawString(this.font, "Equipments", this.panelLeft + 8, this.panelTop + 12, 0xFF676767, false);
        graphics.drawString(this.font, "Equipment detail", this.panelLeft + 139, this.panelTop + 12, 0xFF676767, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}