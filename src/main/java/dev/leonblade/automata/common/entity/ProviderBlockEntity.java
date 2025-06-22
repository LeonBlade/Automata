package dev.leonblade.automata.common.entity;

import dev.leonblade.automata.client.gui.ProviderMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderBlockEntity extends BlockEntity implements MenuProvider {
  private static final String INVENTORY = "inventory";
  private static final String GHOST = "ghost";

  private static final int INPUT_SLOT = 0;
  private static final int OUTPUT_SLOT = 1;
  protected final ContainerData data;
  private int count = 1;

  private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }
  };

  private final ItemStackHandler filterHandler = new ItemStackHandler(1) {
    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
      validateSlotIndex(slot);
      this.stacks.set(slot, stack);
      onContentsChanged(slot);
    }
  };

  public ItemStackHandler getFilterHandler() {
    return this.filterHandler;
  }

  private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

  public ProviderBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.PROVIDER.get(), pos, state);
    this.data = new ContainerData() {
      @Override
      public int get(int index) {
        return ProviderBlockEntity.this.count;
      }

      @Override
      public void set(int index, int value) {
        if (index == 0) {
          ProviderBlockEntity.this.count = value;
        }
      }

      @Override
      public int getCount() {
        return 1;
      }
    };
  }

  @Override
  public void onLoad() {
    super.onLoad();
    this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
  }

  @Override
  public @NotNull Component getDisplayName() {
    return Component.translatable("entity.automata.provider.block_entity");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player player) {
    return new ProviderMenu(id, inv, this, this.data);
  }

  @Override
  public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return lazyItemHandler.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    lazyItemHandler.invalidate();
  }

  @Override
  protected void saveAdditional(CompoundTag nbt) {
    nbt.put(INVENTORY, this.itemHandler.serializeNBT());
    nbt.put(GHOST, this.filterHandler.serializeNBT());
    nbt.putInt("count", this.count);
    super.saveAdditional(nbt);
  }

  @Override
  public void load(@NotNull CompoundTag nbt) {
    super.load(nbt);
    this.itemHandler.deserializeNBT(nbt.getCompound(INVENTORY));
    this.filterHandler.deserializeNBT(nbt.getCompound(GHOST));
    this.data.set(0, nbt.getInt("count"));
  }

  public void drops() {
    if (this.level == null) {
      return;
    }
    var inventory = new SimpleContainer(itemHandler.getSlots());
    for (var i = 0; i < itemHandler.getSlots(); i++) {
      inventory.setItem(i, itemHandler.getStackInSlot(i));
    }
    Containers.dropContents(this.level, this.worldPosition, inventory);
  }

  public void getThatShit() {
    if (this.level == null || this.level.isClientSide) {
      return;
    }
    // Ensure we have an item in the input slot of this block
    var inputStack = this.getFilterHandler().getStackInSlot(0);
    if (inputStack.getCount() == 0) {
      return;
    }

    // Get the backside of the block
    var facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
    // Get the entity that's one block away from the back
    var entity = this.level.getExistingBlockEntity(this.getBlockPos().relative(facing, 1));
    if (entity == null) {
      return;
    }

    // Ensure that this block has an inventory to pull from
    var cap = entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN);
    if (cap.resolve().isEmpty()) {
      return;
    }

    // Resolve the item handler
    var otherItemHandler = cap.resolve().get();
    // Check for a stack that matches the item in our input slot
    var slot = getItemSlot(otherItemHandler, inputStack.getItem());
    if (slot == null) {
      return;
    }

    // Extract the item from the other inventory
    var itemStack = otherItemHandler.extractItem(slot, this.count, false);
    // Insert this into our output slot
    this.itemHandler.insertItem(OUTPUT_SLOT, itemStack, false);
    setChanged();
  }

  public void addCount() {
    this.count++;
    setChanged();
  }

  public int getCount() {
    return this.data.get(0);
  }

  private @Nullable Integer getItemSlot(IItemHandler handle, Item item) {
    for (var i = 0; i < handle.getSlots(); i++) {
      if (handle.getStackInSlot(i).is(item)) {
        return i;
      }
    }
    return null;
  }
}
