package fr.noahboos.essor.client.ui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;

public class EssorEquipmentButton extends Button {
    private ItemStack itemStack = ItemStack.EMPTY;
    private static final int MARGIN = 4;
    private static final int SPACING = 8;

    public EssorEquipmentButton(int x, int y, int width, int height, Component message, OnPress onPress, ItemStack itemStack) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        this.itemStack = itemStack;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        int x = this.getX();
        int y = this.getY();
        int iconX = x + EssorEquipmentButton.MARGIN;
        int centerY = y + this.height / 2;

        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        if (!this.itemStack.isEmpty()) {
            graphics.renderItem(itemStack, iconX, this.getY() + EssorEquipmentButton.MARGIN);
        }

        Component message = this.getMessage();
        int textX = iconX + 16 + EssorEquipmentButton.SPACING;

        graphics.drawString(font, message, textX, centerY - 4, 0xFFFFFFFF);
    }
}