package scaryboi.ghast_stone.world;

import com.google.common.base.Suppliers;
import scaryboi.ghast_stone.Ghast_stone;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import scaryboi.ghast_stone.blocks.ModBlocks;

import java.util.List;
import java.util.function.Supplier;

public class GenerationInit {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Ghast_stone.MODID);

    // Configuration for replacing stone
    private static final Supplier<List<OreConfiguration.TargetBlockState>> GHAST_STONE_REPLACEMENT = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.GHAST_STONE.get().defaultBlockState())
            )
    );

    // Configuration for replacing deepslate
    private static final Supplier<List<OreConfiguration.TargetBlockState>> GHAST_DEEPSLATE_REPLACEMENT = Suppliers.memoize(() ->
            List.of(
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.GHAST_DEEPSLATE.get().defaultBlockState())
            )
    );

    // Configured feature for stone replacement
    public static final RegistryObject<ConfiguredFeature<?, ?>> GHAST_STONE = CONFIGURED_FEATURES.register("ghast_stone",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GHAST_STONE_REPLACEMENT.get(), 7)));

    // Configured feature for deepslate replacement
    public static final RegistryObject<ConfiguredFeature<?, ?>> GHAST_DEEPSLATE = CONFIGURED_FEATURES.register("ghast_deepslate",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(GHAST_DEEPSLATE_REPLACEMENT.get(), 7)));
}
