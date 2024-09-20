package scaryboi.ghast_stone.world;

import scaryboi.ghast_stone.Ghast_stone;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class Generation {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Ghast_stone.MODID);

    // Placed feature for stone replacement
    public static final RegistryObject<PlacedFeature> GHAST_STONE_BLOCKS = PLACED_FEATURES.register("ghast_stone_blocks",
            () -> new PlacedFeature(GenerationInit.GHAST_STONE.getHolder().get(),
                    commonOrePlacement(50,
                            HeightRangePlacement.uniform(
                                    VerticalAnchor.absolute(0),
                                    VerticalAnchor.absolute(128)
                            ))));

    // Placed feature for deepslate replacement
    public static final RegistryObject<PlacedFeature> GHAST_DEEPSLATE_BLOCKS = PLACED_FEATURES.register("ghast_deepslate_blocks",
            () -> new PlacedFeature(GenerationInit.GHAST_DEEPSLATE.getHolder().get(),
                    commonOrePlacement(50,
                            HeightRangePlacement.uniform(
                                    VerticalAnchor.bottom(),
                                    VerticalAnchor.absolute(0)
                            ))));

    private static List<PlacementModifier> commonOrePlacement(int countPerChunk, PlacementModifier height) {
        return List.of(
                CountPlacement.of(countPerChunk),
                InSquarePlacement.spread(),
                height,
                BiomeFilter.biome()
        );
    }
}
