package scaryboi.ghast_stone;

import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import scaryboi.ghast_stone.blocks.ModBlocks;
import scaryboi.ghast_stone.init.ModEntities;
import scaryboi.ghast_stone.world.Generation;
import scaryboi.ghast_stone.world.GenerationInit;

@Mod(Ghast_stone.MODID)
public class Ghast_stone {
    public static final String MODID = "ghast_stone";

    public Ghast_stone() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register entity types
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        Generation.PLACED_FEATURES.register(modEventBus);
        GenerationInit.CONFIGURED_FEATURES.register(modEventBus);

        // Register the attribute creation event listener
        modEventBus.addListener(this::onEntityAttributeCreation);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the block event handler
        MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
    }

    public static final CreativeModeTab GROUP = new CreativeModeTab("Ghast Stone") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.GHAST_STONE.get());
        }
    };

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.STONE_GHAST.get(), Ghast.createAttributes().build());
    }
}
