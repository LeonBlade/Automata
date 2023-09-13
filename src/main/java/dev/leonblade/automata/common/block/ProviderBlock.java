package dev.leonblade.automata.common.block;

import dev.leonblade.automata.common.tile.ModBlockEntities;
import dev.leonblade.automata.common.tile.ProviderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderBlock extends BaseEntityBlock {
  public ProviderBlock(Properties properties) {
    super(properties);
  }

  @Override
  public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
    if (pLevel.isClientSide()) {
      var entity = pLevel.getBlockEntity(pPos);
      if (entity instanceof ProviderBlockEntity) {
        NetworkHooks.openGui((ServerPlayer)pPlayer, (ProviderBlockEntity)entity, pPos);
      } else {
        throw new IllegalStateException("Container provider is missing!");
      }
    }
    return InteractionResult.sidedSuccess(pLevel.isClientSide());
  }

  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
    return ModBlockEntities.PROVIDER.get().create(pPos, pState);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    // return createTickerHelper(pBlockEntityType, ModBlockEntities.PROVIDER.get(), ProviderBlockEntity::tick);
    return super.getTicker(pLevel, pState, pBlockEntityType);
  }
}
