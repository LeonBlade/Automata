package dev.leonblade.automata.util;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Util {
    public static ItemStack copyWithCount(@NotNull ItemStack stack, int count) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        var copy = stack.copy();
        copy.setCount(count);
        return copy;
    }
}
