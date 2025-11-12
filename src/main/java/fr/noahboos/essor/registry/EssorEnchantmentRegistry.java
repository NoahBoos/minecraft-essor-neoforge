package fr.noahboos.essor.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.HashMap;
import java.util.Map;

public class EssorEnchantmentRegistry {
    public static Map<String, Holder<Enchantment>> DEFAULT_ENCHANTMENTS = new HashMap<>();
    private static boolean initialized = false;

    public static void Initialize(RegistryAccess registryAccess) {
        if (initialized) {
            return;
        }

        if (registryAccess == null) {
            throw new IllegalStateException("The minecraft level registry access is null!");
        }

        var enchantmentRegistry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);

        Register(enchantmentRegistry, "unbreaking", Enchantments.UNBREAKING);
        Register(enchantmentRegistry, "protection", Enchantments.PROTECTION);
        Register(enchantmentRegistry, "projectile_protection", Enchantments.PROJECTILE_PROTECTION);
        Register(enchantmentRegistry, "blast_protection", Enchantments.BLAST_PROTECTION);
        Register(enchantmentRegistry, "fire_protection", Enchantments.FIRE_PROTECTION);
        Register(enchantmentRegistry, "respiration", Enchantments.RESPIRATION);
        Register(enchantmentRegistry, "thorns", Enchantments.THORNS);
        Register(enchantmentRegistry, "aqua_affinity", Enchantments.AQUA_AFFINITY);
        Register(enchantmentRegistry, "swift_sneak", Enchantments.SWIFT_SNEAK);
        Register(enchantmentRegistry, "soul_speed", Enchantments.SOUL_SPEED);
        Register(enchantmentRegistry, "feather_falling", Enchantments.FEATHER_FALLING);
        Register(enchantmentRegistry, "depth_strider", Enchantments.DEPTH_STRIDER);
        Register(enchantmentRegistry, "efficiency", Enchantments.EFFICIENCY);
        Register(enchantmentRegistry, "fortune", Enchantments.FORTUNE);
        Register(enchantmentRegistry, "power", Enchantments.POWER);
        Register(enchantmentRegistry, "punch", Enchantments.PUNCH);
        Register(enchantmentRegistry, "multishot", Enchantments.MULTISHOT);
        Register(enchantmentRegistry, "piercing", Enchantments.PIERCING);
        Register(enchantmentRegistry, "quick_charge", Enchantments.QUICK_CHARGE);
        Register(enchantmentRegistry, "density", Enchantments.DENSITY);
        Register(enchantmentRegistry, "breach", Enchantments.BREACH);
        Register(enchantmentRegistry, "wind_burst", Enchantments.WIND_BURST);
        Register(enchantmentRegistry, "smite", Enchantments.SMITE);
        Register(enchantmentRegistry, "bane_of_arthropods", Enchantments.BANE_OF_ARTHROPODS);
        Register(enchantmentRegistry, "sharpness", Enchantments.SHARPNESS);
        Register(enchantmentRegistry, "looting", Enchantments.LOOTING);
        Register(enchantmentRegistry, "loyalty", Enchantments.LOYALTY);
        Register(enchantmentRegistry, "riptide", Enchantments.RIPTIDE);
        Register(enchantmentRegistry, "impaling", Enchantments.IMPALING);
        Register(enchantmentRegistry, "channeling", Enchantments.CHANNELING);
        Register(enchantmentRegistry, "fire_aspect", Enchantments.FIRE_ASPECT);

        initialized = true;
    }

    public static void Register(Registry<Enchantment> registry, String key, ResourceKey<Enchantment> enchantmentResourceKey) {
        DEFAULT_ENCHANTMENTS.put(key, registry.getHolderOrThrow(enchantmentResourceKey));
    }

    public static Holder<Enchantment> GetEnchantmentByID(String id, RegistryAccess registryAccess) {
        if (!initialized) {
            Initialize(registryAccess);
        }

        return DEFAULT_ENCHANTMENTS.get(id);
    }
}
