package dev.leonblade.automata.common.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
  public static final CreativeModeTab AUTOMATA_TAB = new CreativeModeTab("automata_tab") {
    @Override
    public ItemStack makeIcon() {
      return new ItemStack(ModItems.TEST_ITEM.get());
    }
  };
}
