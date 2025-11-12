package fr.noahboos.essor.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.noahboos.essor.Essor;
import fr.noahboos.essor.component.challenge.ChallengeProgress;
import fr.noahboos.essor.component.challenge.Challenges;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EssorDataComponents {
    public static final Codec<ChallengeProgress> CHALLENGE_PROGRESS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(ChallengeProgress::GetId),
                    Codec.INT.fieldOf("currentTier").forGetter(ChallengeProgress::GetCurrentTier),
                    Codec.INT.fieldOf("progress").forGetter(ChallengeProgress::GetProgress)
            ).apply(instance, ChallengeProgress::new)
    );

    public static final StreamCodec<ByteBuf, ChallengeProgress> CHALLENGE_PROGRESS_STREAM_CODEC = StreamCodec.of(
            (buf, value) -> {
                ByteBufCodecs.STRING_UTF8.encode(buf, value.GetId());
                ByteBufCodecs.INT.encode(buf, value.GetCurrentTier());
                ByteBufCodecs.INT.encode(buf, value.GetProgress());
            },
            buf -> new ChallengeProgress(
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.INT.decode(buf),
                    ByteBufCodecs.INT.decode(buf)
            )
    );

    public static final Codec<Challenges> CHALLENGES_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(CHALLENGE_PROGRESS_CODEC)
                            .fieldOf("challenges")
                            .forGetter(Challenges::GetChallenges)
            ).apply(instance, Challenges::new)
    );

    public static final StreamCodec<ByteBuf, Challenges> CHALLENGES_STREAM_CODEC = StreamCodec.of(
            (buf, value) -> {
                ByteBufCodecs.INT.encode(buf, value.GetChallenges().size());
                for (ChallengeProgress progress : value.GetChallenges()) {
                    CHALLENGE_PROGRESS_STREAM_CODEC.encode(buf, progress);
                }
            },
            buf -> {
                int size = ByteBufCodecs.INT.decode(buf);
                List<ChallengeProgress> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    list.add(CHALLENGE_PROGRESS_STREAM_CODEC.decode(buf));
                }
                return new Challenges(list);
            }
    );

    public static final Codec<EquipmentLevelingData> EQUIPMENT_LEVELING_DATA_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("prestige").forGetter(EquipmentLevelingData::GetPrestige),
            Codec.INT.fieldOf("requiredLevelToPrestige").forGetter(EquipmentLevelingData::GetRequiredLevelToPrestige),
            Codec.INT.fieldOf("level").forGetter(EquipmentLevelingData::GetLevel),
            Codec.FLOAT.fieldOf("totalExperienceMultiplier").forGetter(EquipmentLevelingData::GetTotalExperienceMultiplier),
            Codec.FLOAT.fieldOf("prestigeExperienceMultiplier").forGetter(EquipmentLevelingData::GetPrestigeExperienceMultiplier),
            Codec.FLOAT.fieldOf("challengeExperienceMultiplier").forGetter(EquipmentLevelingData::GetChallengeExperienceMultiplier),
            Codec.INT.fieldOf("requiredExperienceToLevelUp").forGetter(EquipmentLevelingData::GetRequiredExperienceToLevelUp),
            Codec.FLOAT.fieldOf("currentExperience").forGetter(EquipmentLevelingData::GetCurrentExperience),
            CHALLENGES_CODEC.fieldOf("challenges").forGetter(EquipmentLevelingData::GetChallenges)
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
            CHALLENGES_STREAM_CODEC.encode(buf, value.GetChallenges());
        },
        buf -> new EquipmentLevelingData(
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            ByteBufCodecs.INT.decode(buf),
            ByteBufCodecs.FLOAT.decode(buf),
            CHALLENGES_STREAM_CODEC.decode(buf)
        )
    );

    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Essor.MODID);

    public static final Supplier<DataComponentType<EquipmentLevelingData>> EQUIPMENT_LEVELING_DATA = REGISTRAR.registerComponentType(
        "equipment_leveling_data",
        builder -> builder
            .persistent(EQUIPMENT_LEVELING_DATA_CODEC)
            .networkSynchronized(EQUIPMENT_LEVELING_DATA_STREAM_CODEC)
    );

    public static final Supplier<DataComponentType<ChallengeProgress>> CHALLENGE_PROGRESS = REGISTRAR.registerComponentType(
        "challenge_progress",
        builder -> builder
            .persistent(CHALLENGE_PROGRESS_CODEC)
            .networkSynchronized(CHALLENGE_PROGRESS_STREAM_CODEC)
    );

    public static final Supplier<DataComponentType<Challenges>> CHALLENGES = REGISTRAR.registerComponentType(
        "challenges",
        builder -> builder
            .persistent(CHALLENGES_CODEC)
            .networkSynchronized(CHALLENGES_STREAM_CODEC)
    );
}