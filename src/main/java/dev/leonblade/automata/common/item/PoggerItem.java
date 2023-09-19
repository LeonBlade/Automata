package dev.leonblade.automata.common.item;

import dev.leonblade.automata.client.gui.ProviderMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoggerItem extends Item implements MenuProvider {
  public PoggerItem(Properties pProperties) {
    super(pProperties.stacksTo(1).rarity(Rarity.RARE));
  }

  @Override
  public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
      // NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((id, inventory, p) -> new ProviderMenu(id, inventory,), Component.literal("Poggers")));
      // var freaks = FrequencyType.QIO.getManagerWrapper().getPublicManager().getFrequencies();
      //
      // if (freaks.size() > 0) {
      //   interact(player, hand);
      // }
    }
    return super.use(level, player, hand);
  }

  private void interact(Player player, InteractionHand hand) {
    // player.openItemGui(this.getDefaultInstance(), hand);
    // var freaks = FrequencyType.QIO.getManagerWrapper().getPublicManager().getFrequencies();
    // var freak = freaks.iterator().next();
    //
    // var offhand = player.getOffhandItem();
    //
    // var desired = 3;
    // var stored = freak.getStored(offhand);
    //
    // // must extract with exact amount
    // if (stored < desired) {
    //   displayMessage(player, "You are missing " + (desired - stored) + " items to retrieve " + desired + " of item " + offhand.getDisplayName().getContents());
    //   return;
    // }
    //
    // // if we have space for the exact amount, we want to extract it
    // if (canGetItem(player, offhand.getItem(), desired)) {
    //   var removed = freak.removeItem(offhand, desired);
    //   player.addItem(removed);
    // }
  }

  private boolean canGetItem(Player player, Item item, int size) {
    // create a stack out of the item specified
    var stack = new ItemStack(item, size);
    // get the player's inventory for faster reference
    var inventory = player.getInventory();
    // get a free slot
    var freeSlot = inventory.getFreeSlot();
    // we have a free slot
    if (inventory.getFreeSlot() >= 0) {
      return true;
    }
    // look for an available slot with space for the given item
    var availableSlot = inventory.getSlotWithRemainingSpace(stack);
    // we have a valid slot
    if (availableSlot >= 0) {
      var availableItem = inventory.getItem(availableSlot);
      return availableItem.getCount() + size <= availableItem.getMaxStackSize();
    }
    return false;
  }

  private void displayMessage(Player player, String text) {
    player.displayClientMessage(Component.literal(text), false);
  }

  @Override
  public @NotNull Component getDisplayName() {
    return Component.literal("Poggers");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, Player player) {
    // return new ProviderMenu(id, inv);
    return null;
  }
}
