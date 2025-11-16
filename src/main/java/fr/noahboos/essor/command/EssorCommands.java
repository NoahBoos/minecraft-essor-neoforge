package fr.noahboos.essor.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import fr.noahboos.essor.component.EquipmentLevelingData;
import fr.noahboos.essor.component.EssorDataComponents;
import fr.noahboos.essor.component.ProgressionManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EssorCommands {
    public static void RegisterCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("essor")
                .then(RegisterSetCommand())
        );
    }

    private static ArgumentBuilder<CommandSourceStack, ?> RegisterSetCommand() {
        return Commands.literal("set")
            .then(RegisterSetPrestigeCommand())
            .then(RegisterSetLevelCommand());
    }

    private static ArgumentBuilder<CommandSourceStack, ?> RegisterSetPrestigeCommand() {
        return Commands.literal("prestige")
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("value", IntegerArgumentType.integer(0, 10))
                    .executes(context -> {
                        ServerPlayer player = EntityArgument.getPlayer(context, "player");
                        ItemStack item = player.getMainHandItem();
                        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                        if (data == null) {
                            context.getSource().sendFailure(Component.literal("This item does not have any EquipmentLevelingData data component. Is it an upgradable item ?"));
                            return 0;
                        }
                        int prestige = IntegerArgumentType.getInteger(context, "value");
                        data.SetPrestige(prestige);
                        data.SetPrestigeExperienceMultiplier(data.GetPrestige() * EquipmentLevelingData.prestigeExperienceMultiplierStep);
                        data.SetTotalExperienceMultiplier();
                        context.getSource().sendSuccess(
                            () -> Component.literal("This item's prestige level has been successfully set to " + prestige + "."),
                            true
                        );
                        return 1;
                    })))
                .then(Commands.literal("up")
                    .then(Commands.argument("player", EntityArgument.player())
                        .executes(context -> {
                            ServerPlayer player = EntityArgument.getPlayer(context, "player");
                            if (player == null) {
                                context.getSource().sendFailure(Component.literal("This command must be triggered by a player."));
                                return 0;
                            }
                            ItemStack item = player.getMainHandItem();
                            EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                            if (data == null) {
                                context.getSource().sendFailure(Component.literal("This item does not have any EquipmentLevelingData data component. Is it an upgradable item ?"));
                                return 0;
                            }
                            data.SetPrestige(data.GetPrestige() + 1);
                            data.SetPrestigeExperienceMultiplier(data.GetPrestige() * EquipmentLevelingData.prestigeExperienceMultiplierStep);
                            data.SetTotalExperienceMultiplier();
                            context.getSource().sendSuccess(
                                () -> Component.literal(
                                    "This item's prestige level has been successfully set to " + data.GetPrestige() + "."
                                ),
                                true
                            );
                            return 1;
                        })));
    }

    private static ArgumentBuilder<CommandSourceStack, ?> RegisterSetLevelCommand() {
        return Commands.literal("level")
            .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("value", IntegerArgumentType.integer(0))
                    .executes(context -> {
                        ServerPlayer player = EntityArgument.getPlayer(context, "player");
                        ItemStack item = player.getMainHandItem();
                        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                        if (data == null) {
                            context.getSource().sendFailure(Component.literal("This item does not have any EquipmentLevelingData data component. Is it an upgradable item ?"));
                            return 0;
                        }
                        int level = IntegerArgumentType.getInteger(context, "value");
                        data.SetLevel(level);
                        ProgressionManager.PrestigeUp(player, item);
                        context.getSource().sendSuccess(
                            () -> Component.literal(
                                "This item's level has been successfully set to " + level +
                                ". The prestige level has been automatically set to " + data.GetPrestige() + "."
                            ),
                            true
                        );
                        return 1;
                    })))
            .then(Commands.literal("up")
                .then(Commands.argument("player", EntityArgument.player())
                    .executes(context -> {
                        ServerPlayer player = EntityArgument.getPlayer(context, "player");
                        if (player == null) {
                            context.getSource().sendFailure(Component.literal("This command must be triggered by a player."));
                            return 0;
                        }
                        ItemStack item = player.getMainHandItem();
                        EquipmentLevelingData data = item.getComponents().get(EssorDataComponents.EQUIPMENT_LEVELING_DATA.get());
                        if (data == null) {
                            context.getSource().sendFailure(Component.literal("This item does not have any EquipmentLevelingData data component. Is it an upgradable item ?"));
                            return 0;
                        }
                        data.SetLevel(data.GetLevel() + 1);
                        ProgressionManager.PrestigeUp(player, item);
                        context.getSource().sendSuccess(
                            () -> Component.literal(
                                "This item's level has been successfully set to " + data.GetLevel() +
                                ". The prestige level has been automatically updated."
                            ),
                            true
                        );
                        return 1;
                    })));
    }
}