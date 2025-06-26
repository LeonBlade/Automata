package dev.leonblade.automata.common.item;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AutomataMod.MOD_ID);

  public static final RegistryObject<CreativeModeTab> AUTOMATA_TAB = CREATIVE_MODE_TABS.register("automata_tab",
      () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.POGGER_ITEM.get()))
          .title(Component.translatable("creative.tab.automata_tab"))
          .displayItems(((pParameters, pOutput) -> {
            pOutput.accept(ModItems.POGGER_ITEM.get());
            pOutput.accept(ModItems.TEST_ITEM.get());
            pOutput.accept(ModBlocks.PROVIDER_BLOCK.get());
            pOutput.accept(ModBlocks.TEST_BLOCK.get());
          })).build());

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}
