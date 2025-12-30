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
import java.util.UUID;

public class EssorTickEventHandler {
    private static Map<UUID, Vec3> lastRecordedPositionInElytra = new HashMap<>();
    private static Map<UUID, Double> totalDistanceFlown = new HashMap<>();
    private static final int DISTANCE_FLOWN_UPDATE_INTERVAL = 4;
    private static Map<UUID, Integer> distanceFlownTicks = new HashMap<>();
    private static Map<UUID, Integer> underwaterTicks = new HashMap<>();

    @SubscribeEvent
    public static void OnLivingTick(PlayerTickEvent.@NotNull Post event) {
        Player player = event.getEntity();
        UUID uuid = player.getUUID();

        if (player.level().isClientSide()) return;

        ItemStack helmetStack = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legsStack = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feetStack = player.getItemBySlot(EquipmentSlot.FEET);

        if (player.isFallFlying()) {
            ProgressionManager.HandleProgress(player, chestStack, EquipmentLevelingData.DEFAULT_XP_ELYTRA_GLIDE);
            distanceFlownTicks.put(uuid, distanceFlownTicks.getOrDefault(uuid, 0) + 1);
            if (distanceFlownTicks.get(uuid) >= DISTANCE_FLOWN_UPDATE_INTERVAL) {
                if (lastRecordedPositionInElytra.get(uuid) == null) {
                    lastRecordedPositionInElytra.put(uuid, player.position());
                } else {
                    totalDistanceFlown.merge(uuid, lastRecordedPositionInElytra.get(uuid).distanceTo(player.position()), Double::sum);
                    lastRecordedPositionInElytra.put(uuid,player.position());
                }
                distanceFlownTicks.put(uuid, 0);
            }
        } else {
            if (lastRecordedPositionInElytra.get(uuid) != null) {
                Challenges.AttemptToLevelUpChallenges(chestStack, (int) Math.round(totalDistanceFlown.get(uuid)), "Essor:Challenge:FlyLongDistance");
                lastRecordedPositionInElytra.remove(uuid);
                totalDistanceFlown.put(uuid, 0.0);
                distanceFlownTicks.put(uuid, 0);
            }
        }
        if (player.isUnderWater()  && (helmetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("respiration", event.getEntity().registryAccess())) >= 1 || EquipmentType.GetEquipmentType(helmetStack) == E_EquipmentType.TURTLE_HELMET)) {
            ProgressionManager.HandleProgress(player, helmetStack, EquipmentLevelingData.DEFAULT_XP_UNDER_WATER_BREATHING);
            underwaterTicks.put(uuid, underwaterTicks.getOrDefault(uuid, 0) + 1);
            if (underwaterTicks.get(uuid) >= 20) {
                Challenges.AttemptToLevelUpChallenges(helmetStack, 1, "Essor:Challenge:BreatheUnderwater");
                underwaterTicks.put(uuid, 0);
            }
        } else underwaterTicks.put(uuid, 0);
        if (player.isCrouching() && (legsStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("swift_sneak", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, legsStack, EquipmentLevelingData.DEFAULT_XP_CROUCHED);
        }
        if ((player.level().getBlockState(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ())).getBlock() == Blocks.SOUL_SAND || player.level().getBlockState(player.blockPosition().below()).getBlock() == Blocks.SOUL_SOIL) && (feetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("soul_speed", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.HandleProgress(player, feetStack, EquipmentLevelingData.DEFAULT_XP_MOVING_ON_SOUL_BLOCKS);
        }
    }
}
