package fr.noahboos.essor.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
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

        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);

        Register(enchantmentRegistryLookup, "unbreaking", Enchantments.UNBREAKING);
        Register(enchantmentRegistryLookup, "protection", Enchantments.PROTECTION);
        Register(enchantmentRegistryLookup, "projectile_protection", Enchantments.PROJECTILE_PROTECTION);
        Register(enchantmentRegistryLookup, "blast_protection", Enchantments.BLAST_PROTECTION);
        Register(enchantmentRegistryLookup, "fire_protection", Enchantments.FIRE_PROTECTION);
        Register(enchantmentRegistryLookup, "respiration", Enchantments.RESPIRATION);
        Register(enchantmentRegistryLookup, "thorns", Enchantments.THORNS);
        Register(enchantmentRegistryLookup, "aqua_affinity", Enchantments.AQUA_AFFINITY);
        Register(enchantmentRegistryLookup, "swift_sneak", Enchantments.SWIFT_SNEAK);
        Register(enchantmentRegistryLookup, "soul_speed", Enchantments.SOUL_SPEED);
        Register(enchantmentRegistryLookup, "feather_falling", Enchantments.FEATHER_FALLING);
        Register(enchantmentRegistryLookup, "depth_strider", Enchantments.DEPTH_STRIDER);
        Register(enchantmentRegistryLookup, "efficiency", Enchantments.EFFICIENCY);
        Register(enchantmentRegistryLookup, "fortune", Enchantments.FORTUNE);
        Register(enchantmentRegistryLookup, "power", Enchantments.POWER);
        Register(enchantmentRegistryLookup, "punch", Enchantments.PUNCH);
        Register(enchantmentRegistryLookup, "multishot", Enchantments.MULTISHOT);
        Register(enchantmentRegistryLookup, "piercing", Enchantments.PIERCING);
        Register(enchantmentRegistryLookup, "quick_charge", Enchantments.QUICK_CHARGE);
        Register(enchantmentRegistryLookup, "density", Enchantments.DENSITY);
        Register(enchantmentRegistryLookup, "breach", Enchantments.BREACH);
        Register(enchantmentRegistryLookup, "wind_burst", Enchantments.WIND_BURST);
        Register(enchantmentRegistryLookup, "smite", Enchantments.SMITE);
        Register(enchantmentRegistryLookup, "bane_of_arthropods", Enchantments.BANE_OF_ARTHROPODS);
        Register(enchantmentRegistryLookup, "sharpness", Enchantments.SHARPNESS);
        Register(enchantmentRegistryLookup, "looting", Enchantments.LOOTING);
        Register(enchantmentRegistryLookup, "loyalty", Enchantments.LOYALTY);
        Register(enchantmentRegistryLookup, "riptide", Enchantments.RIPTIDE);
        Register(enchantmentRegistryLookup, "impaling", Enchantments.IMPALING);
        Register(enchantmentRegistryLookup, "channeling", Enchantments.CHANNELING);
        Register(enchantmentRegistryLookup, "fire_aspect", Enchantments.FIRE_ASPECT);
        Register(enchantmentRegistryLookup, "sweeping_edge", Enchantments.SWEEPING_EDGE);

        initialized = true;
    }

    public static void Register(HolderLookup.RegistryLookup<Enchantment> lookup, String key, ResourceKey<Enchantment> enchantmentResourceKey) {
        DEFAULT_ENCHANTMENTS.put(key, lookup.getOrThrow(enchantmentResourceKey));
    }

    public static Holder<Enchantment> GetEnchantmentByID(String id, RegistryAccess registryAccess) {
        if (!initialized) {
            Initialize(registryAccess);
        }

        return DEFAULT_ENCHANTMENTS.get(id);
    }
}
