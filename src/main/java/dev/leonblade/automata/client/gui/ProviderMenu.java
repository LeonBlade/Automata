package dev.leonblade.automata.client.gui;

import dev.leonblade.automata.common.entity.ProviderBlockEntity;
import dev.leonblade.automata.common.inventory.GhostSlot;
import dev.leonblade.automata.common.inventory.OutputSlotHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ProviderMenu extends BaseAutomataMenu<ProviderBlockEntity> {

  public ProviderMenu(int id, Inventory inv, FriendlyByteBuf buf) {
    this(id, inv, inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(1));
  }

  public ProviderMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
    super(ModMenuTypes.PROVIDER_MENU.get(), id);

    checkContainerSize(inv, 1);

    this.blockEntity = (ProviderBlockEntity) entity;
    this.level = inv.player.level();
    this.data = data;

    this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
      // Input slot
      this.addSlot(new SlotItemHandler(handler, 0, 8, 8));
      // Ghost slot
      this.addSlot(new GhostSlot(this.blockEntity.getFilterHandler(), 0, 8, 30));
      // Output slot
      this.addSlot(new OutputSlotHandler(handler, 1, 152, 8));
    });

    // Add the player inventory slots
    addPlayerInventory(inv);
    // Add the player hotbar slots
    addPlayerHotbar(inv);

    this.addDataSlots(this.data);
  }

  @Override
  public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
    var stack = ItemStack.EMPTY;
    var slot = this.slots.get(index);
    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    };

    var item = slot.getItem();
    stack = item.copy();

    // move from output slot to the inventory (39 = inventory rows (4) * row item count (9) + this menu's slot count (3)
    if (index == 2) {
      if (!this.moveItemStackTo(item, 3, 39, true)) {
        return ItemStack.EMPTY;
      }
    } else if (index != 0 && index != 1) { // the player inventory
      // perform checks to see if the item can be put in the menu
      if (!this.moveItemStackTo(item, 0, 1, false)) {
        return ItemStack.EMPTY;
      }
    } else if (!this.moveItemStackTo(item, 3, 39, false)) {
      return ItemStack.EMPTY;
    }

    if (item.isEmpty()) {
      slot.setByPlayer(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    return stack;
  }
}
