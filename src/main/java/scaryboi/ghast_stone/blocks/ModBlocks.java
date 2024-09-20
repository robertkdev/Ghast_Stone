package scaryboi.ghast_stone.blocks;

import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import scaryboi.ghast_stone.Ghast_stone;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Ghast_stone.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ghast_stone.MODID);

    // Updated block registrations
    public static final RegistryObject<Block> GHAST_STONE = registerBlock("ghast_stone", Material.STONE, 0.75F, 6.0F, SoundType.STONE);
    public static final RegistryObject<Block> GHAST_DEEPSLATE = registerBlock("ghast_deepslate", Material.STONE, 0.75F, 6.0F, SoundType.DEEPSLATE);
    public static final RegistryObject<Block> GHAST_ANDESITE = registerBlock("ghast_andesite", Material.STONE, 0.75F, 6.0F, SoundType.STONE);
    public static final RegistryObject<Block> GHAST_DIORITE = registerBlock("ghast_diorite", Material.STONE, 0.75F, 6.0F, SoundType.STONE);
    public static final RegistryObject<Block> GHAST_GRANITE = registerBlock("ghast_granite", Material.STONE, 0.75F, 6.0F, SoundType.STONE);

    private static RegistryObject<Block> registerBlock(String name, Material material, float strength, float resistance, SoundType soundtype) {
        return registerBlockWithItem(name,
                () -> new Block(BlockBehaviour.Properties.of(material)
                        .strength(strength, resistance)
                        .requiresCorrectToolForDrops()
                        .sound(soundtype)));
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties().tab(Ghast_stone.GROUP)));
        return registeredBlock;
    }
}

