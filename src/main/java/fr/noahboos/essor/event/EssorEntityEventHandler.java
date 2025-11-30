package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ActionBar;
import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorEnchantmentRegistry;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.E_EquipmentType;
import fr.noahboos.essor.util.EquipmentType;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;

public class EssorEntityEventHandler {
    @SubscribeEvent
    public static void OnEntityHurt(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            LivingEntity entity = event.getEntity();
            Entity attacker = event.getSource().getEntity();
            if (entity instanceof Player player) {
                float damage = event.getOriginalDamage() - event.getNewDamage();
                float armorExperience = damage * EquipmentLevelingData.DEFAULT_XP_DAMAGE_TAKEN;
                Iterable<ItemStack> armor = InventoryUtils.GetPlayerArmor(player.getInventory());
                for (ItemStack item : armor) {
                    if (item instanceof ItemStack) {
                        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                        if (data == null) continue;
                        ProgressionManager.AddExperience(item, armorExperience);
                        ProgressionManager.LevelUp(player, item);
                        ProgressionManager.PrestigeUp(player, item);
                    }
                }
                float shieldExperience = event.getBlockedDamage() * EquipmentLevelingData.DEFAULT_XP_SHIELD_BLOCK;
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.AddExperience(heldItem, shieldExperience);
                    ProgressionManager.LevelUp(player, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }

                ItemStack offHandItem = player.getOffhandItem();
                if (offHandItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.AddExperience(offHandItem, shieldExperience);
                    ProgressionManager.LevelUp(player, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                InventoryUtils.InventorySync((ServerPlayer) player);
            }

            if (attacker instanceof Player player) {
                float experience = (float) (Math.ceil((event.getOriginalDamage() * EquipmentLevelingData.DEFAULT_XP_DAMAGE_DEALT) * 2) / 2);

                ItemStack heldItem = player.getMainHandItem();
                if (!(heldItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.AddExperience(heldItem, experience);
                    ProgressionManager.LevelUp(player, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }


                ItemStack offHandItem = player.getOffhandItem();
                if (!(offHandItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.AddExperience(offHandItem, experience);
                    ProgressionManager.LevelUp(player, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnEntityDeath(LivingDeathEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        } else {
            LivingEntity entity = event.getEntity();
            Entity killer = event.getSource().getEntity();
            if (killer instanceof Player player) {
                ItemStack heldItem = player.getMainHandItem();
                EssorRegistry.ExperienceResult heldItemResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, heldItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (heldItemResult.isRewardable()) {
                    ProgressionManager.AddExperience(heldItem, heldItemResult.experience());
                    ProgressionManager.LevelUp(player, heldItem);
                    ProgressionManager.PrestigeUp(player, heldItem);
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                ActionBar.DisplayXPCount((ServerPlayer) player, heldItem);

                ItemStack offHandItem = player.getOffhandItem();
                EssorRegistry.ExperienceResult offHandResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (offHandResult.isRewardable()) {
                    ProgressionManager.AddExperience(offHandItem, offHandResult.experience());
                    ProgressionManager.LevelUp(player, offHandItem);
                    ProgressionManager.PrestigeUp(player, offHandItem);
                }
                Challenges.AttemptToLevelUpChallenges(offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnLivingTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;

        ItemStack helmetStack = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legsStack = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feetStack = player.getItemBySlot(EquipmentSlot.FEET);

        if (player.isFallFlying()) {
            ProgressionManager.AddExperience(chestStack, EquipmentLevelingData.DEFAULT_XP_ELYTRA_GLIDE);
            ProgressionManager.LevelUp(player, chestStack);
            ProgressionManager.PrestigeUp(player, chestStack);
        }
        if (player.isUnderWater() && (helmetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("respiration", event.getEntity().registryAccess())) >= 1 || EquipmentType.GetEquipmentType(helmetStack) == E_EquipmentType.TURTLE_HELMET)) {
            ProgressionManager.AddExperience(helmetStack, EquipmentLevelingData.DEFAULT_XP_UNDER_WATER_BREATHING);
            ProgressionManager.LevelUp(player, helmetStack);
            ProgressionManager.PrestigeUp(player, helmetStack);
        }
        if (player.isCrouching() && (legsStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("swift_sneak", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.AddExperience(legsStack, EquipmentLevelingData.DEFAULT_XP_CROUCHED);
            ProgressionManager.LevelUp(player, legsStack);
            ProgressionManager.PrestigeUp(player, legsStack);
        }
        if ((player.level().getBlockState(new BlockPos(player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ())).getBlock() == Blocks.SOUL_SAND || player.level().getBlockState(player.blockPosition().below()).getBlock() == Blocks.SOUL_SOIL) && (feetStack.getEnchantmentLevel(EssorEnchantmentRegistry.GetEnchantmentByID("soul_speed", event.getEntity().registryAccess())) >= 1)) {
            ProgressionManager.AddExperience(feetStack, EquipmentLevelingData.DEFAULT_XP_MOVING_ON_SOUL_BLOCKS);
            ProgressionManager.LevelUp(player, feetStack);
            ProgressionManager.PrestigeUp(player, feetStack);
        }
    }
}
