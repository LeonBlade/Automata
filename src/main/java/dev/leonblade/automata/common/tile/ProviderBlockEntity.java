package dev.leonblade.automata.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderBlockEntity extends BlockEntity implements MenuProvider {

  public ProviderBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.PROVIDER.get(), pPos, pBlockState);
  }

  @Override
  public @NotNull Component getDisplayName() {
    return new TranslatableComponent("entity.automata.provider.block_entity");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
    return null;
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    return super.getCapability(cap, side);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag) {
    pTag.put("pog", StringTag.valueOf("poggers"));
    super.saveAdditional(pTag);
  }

  @Override
  public void load(CompoundTag pTag) {
    super.load(pTag);
    var pog = pTag.getString("pog");
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ProviderBlockEntity entity) {
    if (level.isClientSide()) {
      return;
    }
  }
}
