package fr.noahboos.essor.event;

import fr.noahboos.essor.component.ActionBar;
import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import fr.noahboos.essor.component.challenge.Challenges;
import fr.noahboos.essor.registry.EssorRegistry;
import fr.noahboos.essor.util.InventoryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

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
                        ProgressionManager.HandleProgress(player, item, armorExperience);
                    }
                }
                float shieldExperience = event.getBlockedDamage() * EquipmentLevelingData.DEFAULT_XP_SHIELD_BLOCK;
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.HandleProgress(player, heldItem, shieldExperience);
                }

                ItemStack offHandItem = player.getOffhandItem();
                if (offHandItem.getItem() instanceof ShieldItem) {
                    ProgressionManager.HandleProgress(player, offHandItem, shieldExperience);
                }
                InventoryUtils.InventorySync((ServerPlayer) player);
            }

            if (attacker instanceof Player player) {
                float experience = (float) (Math.ceil((event.getOriginalDamage() * EquipmentLevelingData.DEFAULT_XP_DAMAGE_DEALT) * 2) / 2);

                ItemStack heldItem = player.getMainHandItem();
                if (!(heldItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.HandleProgress(player, heldItem, experience);
                }


                ItemStack offHandItem = player.getOffhandItem();
                if (!(offHandItem.getItem() instanceof ShieldItem)) {
                    ProgressionManager.HandleProgress(player, offHandItem, experience);
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
                    ProgressionManager.HandleProgress(player, heldItem, heldItemResult.experience());
                }
                Challenges.AttemptToLevelUpChallenges(heldItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                ActionBar.DisplayXPCount((ServerPlayer) player, heldItem);

                ItemStack offHandItem = player.getOffhandItem();
                EssorRegistry.ExperienceResult offHandResult = EssorRegistry.GetExperience(EssorRegistry.PRIMARY_ACTION_EXPERIENCE_TABLES, offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                if (offHandResult.isRewardable()) {
                    ProgressionManager.HandleProgress(player, offHandItem, offHandResult.experience());
                }
                Challenges.AttemptToLevelUpChallenges(offHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
                InventoryUtils.InventorySync((ServerPlayer) player);
            }
        }
    }

    @SubscribeEvent
    public static void OnEntityRightClick(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity().level().isClientSide()) return;
        ServerPlayer player = (ServerPlayer) event.getEntity();
        Entity entity = event.getTarget();
        ItemStack mainHandItem = player.getMainHandItem();
        EssorRegistry.ExperienceResult mainItemResult = EssorRegistry.GetExperience(EssorRegistry.SECOND_ACTION_EXPERIENCE_TABLES, mainHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
        if (mainItemResult.isRewardable()) {
            if (entity instanceof Sheep && ((Sheep) entity).isSheared()) return;
            if (entity instanceof SnowGolem && !((SnowGolem) entity).hasPumpkin()) return;
            ProgressionManager.HandleProgress(player, mainHandItem, mainItemResult.experience());
        }
        Challenges.AttemptToLevelUpChallenges(mainHandItem, BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
        InventoryUtils.InventorySync(player);
    }
}
