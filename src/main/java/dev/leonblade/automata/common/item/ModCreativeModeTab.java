package dev.leonblade.automata.common.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
  public static final CreativeModeTab AUTOMATA_TAB = new CreativeModeTab("automata_tab") {
    @Override
    public @NotNull ItemStack makeIcon() {
      return new ItemStack(ModItems.TEST_ITEM.get());
    }
  };
}
