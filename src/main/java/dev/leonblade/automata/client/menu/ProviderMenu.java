package dev.leonblade.automata.client.menu;

import dev.leonblade.automata.common.block.ModBlocks;
import dev.leonblade.automata.common.tile.ProviderBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProviderMenu extends AbstractContainerMenu {
  public ProviderBlockEntity blockEntity;

  protected ProviderMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
    super(pMenuType, pContainerId);
  }

  @Override
  public boolean stillValid(@NotNull Player pPlayer) {
    return stillValid(ContainerLevelAccess.create(pPlayer.getLevel(), blockEntity.getBlockPos()), pPlayer, ModBlocks.PROVIDER_BLOCK.get());
  }
}
