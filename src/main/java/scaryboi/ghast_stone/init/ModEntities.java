package scaryboi.ghast_stone.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import scaryboi.ghast_stone.Ghast_stone;
import scaryboi.ghast_stone.entity.StoneGhast;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ghast_stone.MODID);

    public static final RegistryObject<EntityType<StoneGhast>> STONE_GHAST =
            ENTITY_TYPES.register("stone_ghast",
                    () -> EntityType.Builder.<StoneGhast>of(StoneGhast::new, MobCategory.MONSTER)
                            .sized(1.0F, 1.0F)
                            .build(Ghast_stone.MODID + ":stone_ghast"));
}
