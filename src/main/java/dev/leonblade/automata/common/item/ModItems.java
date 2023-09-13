package dev.leonblade.automata.common.item;

import dev.leonblade.automata.AutomataMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AutomataMod.MOD_ID);

  public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().tab(ModCreativeModeTab.AUTOMATA_TAB)));
  public static final RegistryObject<Item> POGGER_ITEM = ITEMS.register("pogger_item", () -> new PoggerItem(new Item.Properties().tab(ModCreativeModeTab.AUTOMATA_TAB)));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
