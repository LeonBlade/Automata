package dev.leonblade.automata.common.datagen;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
  public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, AutomataMod.MOD_ID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.@NotNull Provider pProvider) {
    this.tag(BlockTags.NEEDS_STONE_TOOL)
        .add(
            ModBlocks.PROVIDER_BLOCK.get(),
            ModBlocks.TEST_BLOCK.get()
        );

    this.tag(BlockTags.MINEABLE_WITH_AXE)
        .add(ModBlocks.TEST_BLOCK.get());

    this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .add(ModBlocks.PROVIDER_BLOCK.get());
  }
}
