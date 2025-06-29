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

    // Add the player inventory slots
    addPlayerInventory(inv);
    // Add the player hotbar slots
    addPlayerHotbar(inv);

    this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
      // Input slot
      this.addSlot(new SlotItemHandler(handler, 0, 8, 8));
      // Ghost slot
      this.addSlot(new GhostSlot(this.blockEntity.getFilterHandler(), 0, 8, 30));
      // Output slot
      this.addSlot(new OutputSlotHandler(handler, 1, 152, 8));
    });

    this.addDataSlots(this.data);
  }

  // NOTE: I'm not going to be using this, but keeping it here temporarily

  // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
  // must assign a slot number to each of the slots used by the GUI.
  // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
  // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
  //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
  //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
  //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
  private static final int HOTBAR_SLOT_COUNT = 9;
  private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
  private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
  private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
  private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
  private static final int VANILLA_FIRST_SLOT_INDEX = 0;
  private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

  // THIS YOU HAVE TO DEFINE!
  private static final int TE_INVENTORY_SLOT_COUNT = 2;  // must be the number of slots you have!

  @Override
  public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
    var currentSlot = this.slots.get(index);
    // TODO: fix this
    if (!currentSlot.hasItem()) {
      return ItemStack.EMPTY;
    }

    return ItemStack.EMPTY;
    // var sourceSlot = slots.get(index);
    // if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
    // var sourceStack = sourceSlot.getItem();
    // var copyOfSourceStack = sourceStack.copy();
    //
    // // Check if the slot clicked is one of the vanilla container slots
    // if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
    //   // This is a vanilla container slot so merge the stack into the tile inventory
    //   if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
    //     + TE_INVENTORY_SLOT_COUNT, false)) {
    //     return ItemStack.EMPTY;  // EMPTY_ITEM
    //   }
    // } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
    //   // This is a TE slot so merge the stack into the players inventory
    //   if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
    //     return ItemStack.EMPTY;
    //   }
    // } else {
    //   System.out.println("Invalid slotIndex:" + index);
    //   return ItemStack.EMPTY;
    // }
    // // If stack size == 0 (the entire stack was moved) set slot contents to null
    // if (sourceStack.getCount() == 0) {
    //   sourceSlot.set(ItemStack.EMPTY);
    // } else {
    //   sourceSlot.setChanged();
    // }
    // sourceSlot.onTake(playerIn, sourceStack);
    // return copyOfSourceStack;
  }
}
