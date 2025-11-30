package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.registry.EssorEnchantmentRegistry;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

public class EssorTickEventHandler {
    @SubscribeEvent
    public static void OnLivingTick(PlayerTickEvent.@NotNull Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ItemStack helmetStack = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legsStack = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feetStack = player.getItemBySlot(EquipmentSlot.FEET);

        if (player.isFallFlying()) {
            ProgressionManager.HandleProgress(player, chestStack, EquipmentLevelingData.DEFAULT_XP_ELYTRA_GLIDE);
        }
        if (player.isUnderWater() && (helmetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("respiration", event.getEntity().registryAccess())) >= 1 || EquipmentType.GetEquipmentType(helmetStack) == E_EquipmentType.TURTLE_HELMET)) {
            ProgressionManager.HandleProgress(player, helmetStack, EquipmentLevelingData.DEFAULT_XP_UNDER_WATER_BREATHING);
        }
        if (player.isCrouching() && (legsStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("swift_sneak", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, legsStack, EquipmentLevelingData.DEFAULT_XP_CROUCHED);
        }
        if ((player.level().getBlockState(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ())).getBlock() == Blocks.SOUL_SAND || player.level().getBlockState(player.blockPosition().below()).getBlock() == Blocks.SOUL_SOIL) && (feetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("soul_speed", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, feetStack, EquipmentLevelingData.DEFAULT_XP_MOVING_ON_SOUL_BLOCKS);
        }
    }
}
