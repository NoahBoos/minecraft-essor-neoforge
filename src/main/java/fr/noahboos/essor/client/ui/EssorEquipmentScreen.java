package fr.noahboos.essor.client.ui;

import fr.noahboos.essor.Essor;
import fr.noahboos.essor.client.EssorKeyMappings;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.client.ui.button.EssorEquipmentButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EssorEquipmentScreen extends Screen {
    private List<ItemStack> upgradableItemsInInventory = new ArrayList<>();
    private final int panelWidth = 384;
    private final int panelHeight = 192;
    private int panelLeft;
    private int panelTop;
    private static final ResourceLocation PANEL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Essor.MODID, "textures/gui/container/equipment_panel.png");
    private int inventorySectionScrollOffset = 0;
    private final int inventorySectionScrollSpeed = 24;
    private final int inventorySectionTopMargin = 30;
    private final int inventorySectionBottomMargin = 15;
    private final int inventorySectionVisibleHeight = this.panelHeight - this.inventorySectionTopMargin - this.inventorySectionBottomMargin;

    public EssorEquipmentScreen() {
        super(Component.literal("Essor - Equipment panel"));
    }

    @Override
    protected void init() {
        this.panelLeft = (this.width - this.panelWidth) / 2;
        this.panelTop = (this.height - this.panelHeight) / 2;
        Minecraft.getInstance().player.getInventory().forEach(itemStack -> {
            if (itemStack.getComponents().has(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get())) {
                this.upgradableItemsInInventory.add(itemStack);
            }
        });
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.clearWidgets();
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        this.renderTitles(graphics);
        this.renderInventory(graphics);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.fill(0, 0, this.width, this.height, 0x91101010);
        graphics.blit(RenderPipelines.GUI_TEXTURED, PANEL_TEXTURE, this.panelLeft, this.panelTop, 0, 0, this.panelWidth, this.panelHeight, 384, 192);
    }

    public void renderTitles(GuiGraphics graphics) {
        graphics.drawString(this.font, "Equipments", this.panelLeft + 8, this.panelTop + 12, 0xFF676767, false);
        graphics.drawString(this.font, "Equipment detail", this.panelLeft + 167, this.panelTop + 12, 0xFF676767, false);
    }

    public void renderInventory(GuiGraphics graphics) {
        int equipmentButtonWidth = 140;
        int equipmentButtonHeight = 24;
        int equipmentListLeft = this.panelLeft + 12;
        int topVisibleY = this.panelTop + this.inventorySectionTopMargin;
        int bottomVisibleY = topVisibleY + this.inventorySectionVisibleHeight;

        for (int i = 0; i < upgradableItemsInInventory.size(); i++) {
            int equipmentButtonTop = topVisibleY + (i * equipmentButtonHeight) - this.inventorySectionScrollOffset;

            if (equipmentButtonTop < topVisibleY || equipmentButtonTop > bottomVisibleY || equipmentButtonTop + equipmentButtonHeight > bottomVisibleY) continue;

            this.addRenderableWidget(new EssorEquipmentButton(
                    equipmentListLeft, equipmentButtonTop,
                    equipmentButtonWidth, equipmentButtonHeight,
                    Component.literal(upgradableItemsInInventory.get(i).getDisplayName().getString().replace("[", "").replace("]", "")),
                    button -> {},
                    upgradableItemsInInventory.get(i)
            ));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (EssorKeyMappings.OPEN_EQUIPMENT_GUI.getKey().getValue() == keyCode) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int equipmentButtonHeight = 24;
        int maxScroll = Math.max(0, (upgradableItemsInInventory.size() * equipmentButtonHeight) - this.inventorySectionVisibleHeight);

        this.inventorySectionScrollOffset -= (int) (scrollY * this.inventorySectionScrollSpeed);

        this.inventorySectionScrollOffset = Math.max(0, Math.min(this.inventorySectionScrollOffset, maxScroll));
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}