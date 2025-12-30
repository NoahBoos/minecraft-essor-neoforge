package fr.noahboos.essor.event;

import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorEnchantmentRegistry;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EssorTickEventHandler {
    private static Vec3 lastRecordedPositionInElytra = null;
    private static double totalDistanceFlown = 0.0;
    private static final int DISTANCE_FLOWN_UPDATE_INTERVAL = 4;
    private static int distanceFlownTicks = 0;
    private static Map<Player, Integer> underwaterTicks = new HashMap<>();

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
            distanceFlownTicks++;
            if (distanceFlownTicks >= DISTANCE_FLOWN_UPDATE_INTERVAL) {
                if (lastRecordedPositionInElytra == null) {
                    lastRecordedPositionInElytra = player.position();
                } else {
                    totalDistanceFlown += lastRecordedPositionInElytra.distanceTo(player.position());
                    lastRecordedPositionInElytra = player.position();
                }
                distanceFlownTicks = 0;
            }
        } else {
            if (lastRecordedPositionInElytra != null) {
                Challenges.AttemptToLevelUpChallenges(chestStack, (int) Math.round(totalDistanceFlown), "Essor:Challenge:FlyLongDistance");
                lastRecordedPositionInElytra = null;
                totalDistanceFlown = 0.0;
                distanceFlownTicks = 0;
            }
        }
        if (player.isUnderWater()  && (helmetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("respiration", event.getEntity().registryAccess())) >= 1 || EquipmentType.GetEquipmentType(helmetStack) == E_EquipmentType.TURTLE_HELMET)) {
            ProgressionManager.HandleProgress(player, helmetStack, EquipmentLevelingData.DEFAULT_XP_UNDER_WATER_BREATHING);
            underwaterTicks.put(player, underwaterTicks.getOrDefault(player, 0) + 1);
            if (underwaterTicks.get(player) >= 20) {
                Challenges.AttemptToLevelUpChallenges(helmetStack, 1, "Essor:Challenge:BreatheUnderwater");
                underwaterTicks.put(player, 0);
            }
        } else underwaterTicks.put(player, 0);
        if (player.isCrouching() && (legsStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("swift_sneak", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, legsStack, EquipmentLevelingData.DEFAULT_XP_CROUCHED);
        }
        if ((player.level().getBlockState(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ())).getBlock() == Blocks.SOUL_SAND || player.level().getBlockState(player.blockPosition().below()).getBlock() == Blocks.SOUL_SOIL) && (feetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("soul_speed", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, feetStack, EquipmentLevelingData.DEFAULT_XP_MOVING_ON_SOUL_BLOCKS);
        }
    }
}
