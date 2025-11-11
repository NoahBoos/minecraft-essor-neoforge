package fr.noahboos.essor.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.noahboos.essor.Essor;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EssorDataComponents {
    public static final Codec<EquipmentLevelingData> EQUIPMENT_LEVELING_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("prestige").forGetter(EquipmentLevelingData::GetPrestige),
                    Codec.INT.fieldOf("requiredLevelToPrestige").forGetter(EquipmentLevelingData::GetRequiredLevelToPrestige),
                    Codec.INT.fieldOf("level").forGetter(EquipmentLevelingData::GetLevel),
                    Codec.FLOAT.fieldOf("totalExperienceMultiplier").forGetter(EquipmentLevelingData::GetTotalExperienceMultiplier),
                    Codec.FLOAT.fieldOf("prestigeExperienceMultiplier").forGetter(EquipmentLevelingData::GetPrestigeExperienceMultiplier),
                    Codec.FLOAT.fieldOf("challengeExperienceMultiplier").forGetter(EquipmentLevelingData::GetChallengeExperienceMultiplier),
                    Codec.INT.fieldOf("requiredExperienceToLevelUp").forGetter(EquipmentLevelingData::GetRequiredExperienceToLevelUp),
                    Codec.FLOAT.fieldOf("currentExperience").forGetter(EquipmentLevelingData::GetCurrentExperience)
            ).apply(instance, EquipmentLevelingData::new)
    );

    public static final StreamCodec<ByteBuf, EquipmentLevelingData> EQUIPMENT_LEVELING_DATA_STREAM_CODEC = StreamCodec.of(
            (buf, value) -> {
                ByteBufCodecs.INT.encode(buf, value.GetPrestige());
                ByteBufCodecs.INT.encode(buf, value.GetRequiredLevelToPrestige());
                ByteBufCodecs.INT.encode(buf, value.GetLevel());
                ByteBufCodecs.FLOAT.encode(buf, value.GetTotalExperienceMultiplier());
                ByteBufCodecs.FLOAT.encode(buf, value.GetPrestigeExperienceMultiplier());
                ByteBufCodecs.FLOAT.encode(buf, value.GetChallengeExperienceMultiplier());
                ByteBufCodecs.INT.encode(buf, value.GetRequiredExperienceToLevelUp());
                ByteBufCodecs.FLOAT.encode(buf, value.GetCurrentExperience());
            },
            buf -> new EquipmentLevelingData(
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.FLOAT.decode(buf),
                    ByteBufCodecs.FLOAT.decode(buf),
                    ByteBufCodecs.FLOAT.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.FLOAT.decode(buf)
            )
    );

    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Essor.MODID);

    public static final Supplier<DataComponentType<EquipmentLevelingData>> EQUIPMENT_LEVELING_DATA = REGISTRAR.registerComponentType(
            "equipment_leveling_data",
            builder -> builder
                    .persistent(EQUIPMENT_LEVELING_DATA_CODEC)
                    .networkSynchronized(EQUIPMENT_LEVELING_DATA_STREAM_CODEC)
    );
}