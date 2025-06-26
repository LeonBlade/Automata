package dev.leonblade.automata.common.item;

import dev.leonblade.automata.AutomataMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AutomataMod.MOD_ID);
  public static final Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>(){});

  public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item",
      () -> new Item(new Item.Properties()));
  public static final RegistryObject<Item> POGGER_ITEM = ITEMS.register("pogger_item",
      () -> new PoggerItem(new Item.Properties()));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
