package dev.leonblade.automata.common.block;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AutomataMod.MOD_ID);

  public static final RegistryObject<Block> TEST_BLOCK = registerBlock(
    "test_block",
    () -> new Block(
      BlockBehaviour.Properties.of()
          .mapColor(MapColor.COLOR_PURPLE)
          .sound(SoundType.WOOD)
          .strength(3f)
          .requiresCorrectToolForDrops()
      )
  );

  public static final RegistryObject<ProviderBlock> PROVIDER_BLOCK = registerBlock(
    "provider_block",
    () -> new ProviderBlock(
      BlockBehaviour.Properties.of()
          .sound(SoundType.METAL)
          .strength(2f)
          .requiresCorrectToolForDrops()
    )
  );

  private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
    ModItems.ITEMS.register(
      name,
      () -> new BlockItem(block.get(), new Item.Properties())
    );
  }

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
