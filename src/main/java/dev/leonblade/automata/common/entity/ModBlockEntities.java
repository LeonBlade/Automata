package dev.leonblade.automata.common.entity;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AutomataMod.MOD_ID);

  public static final RegistryObject<BlockEntityType<ProviderBlockEntity>> PROVIDER = BLOCK_ENTITIES.register("provider", () -> BlockEntityType.Builder.of(ProviderBlockEntity::new, ModBlocks.PROVIDER_BLOCK.get()).build(null));

  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}
