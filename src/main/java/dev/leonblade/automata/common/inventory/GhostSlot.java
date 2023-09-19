package dev.leonblade.automata.common.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GhostSlot extends SlotItemHandler {

  public GhostSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public void set(@NotNull ItemStack stack) {
    if (!stack.isEmpty()) {
      stack.setCount(1);
    }
    super.set(stack);
  }

  @Override
  public boolean mayPickup(Player playerIn) {
    return false;
  }
}
