package dev.leonblade.automata.client.gui;

import com.mojang.logging.LogUtils;
import dev.leonblade.automata.common.block.ModBlocks;
import dev.leonblade.automata.common.inventory.GhostSlot;
import dev.leonblade.automata.util.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseAutomataMenu<T extends BlockEntity> extends AbstractContainerMenu {
    protected T blockEntity;
    protected Level level;
    protected ContainerData data;

    protected BaseAutomataMenu(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.PROVIDER_BLOCK.get());
    }

    /**
     * Adds the player inventory to the menu
     * @param inventory Inventory to display, typically this is the player
     */
    protected void addPlayerInventory(Inventory inventory) {
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 86 + y * 18));
            }
        }
    }

    /**
     * Adds the hotbar to the menu
     * @param inventory Inventory to display, typically this is the player
     */
    protected void addPlayerHotbar(Inventory inventory) {
        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 144));
        }
    }

    // Allows for ghost slot logic
    @Override
    public void clicked(int id, int dragType, @NotNull ClickType type, @NotNull Player player) {
        // Provide logic for handling ghost slots
        try {
            var slot = this.getSlot(id);
            if (slot instanceof GhostSlot) {
                var carried = Util.copyWithCount(player.containerMenu.getCarried(), 1);
                if (carried.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else if (slot.mayPlace(carried)) {
                    slot.set(carried);
                }
                return;
            }
        } catch (IndexOutOfBoundsException e) {
            LogUtils.getLogger().error("Could not click slot with index: {}", id, e);
        }
        super.clicked(id, dragType, type, player);
    }
}
