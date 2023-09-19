package dev.leonblade.automata.common.block;

import dev.leonblade.automata.common.entity.ModBlockEntities;
import dev.leonblade.automata.common.entity.ProviderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderBlock extends BaseEntityBlock {
  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

  public ProviderBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
  }

  @Override
  public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
    if (!level.isClientSide()) {
      var entity = level.getBlockEntity(pos);
      if (entity instanceof ProviderBlockEntity) {
        NetworkHooks.openScreen((ServerPlayer)player, (ProviderBlockEntity)entity, pos);
      } else {
        throw new IllegalStateException("Container provider is missing!");
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide());
  }

  @Override
  public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.MODEL;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
    return new ProviderBlockEntity(pos, state);
  }

  // @Nullable
  // @Override
  // public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
  //   return createTickerHelper(entityType, ModBlockEntities.PROVIDER.get(), ProviderBlockEntity::tick);
  // }

  @Override
  public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState, boolean isMoving) {
    if (state.getBlock() != blockState.getBlock()) {
      var blockEntity = level.getBlockEntity(pos);
      if (blockEntity instanceof ProviderBlockEntity) {
        ((ProviderBlockEntity)blockEntity).drops();
      }
    }
    super.onRemove(state, level, pos, blockState, isMoving);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
  }
}
