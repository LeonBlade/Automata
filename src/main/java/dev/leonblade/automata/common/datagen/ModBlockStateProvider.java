package dev.leonblade.automata.common.datagen;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AutomataMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
      blockWithItem(ModBlocks.TEST_BLOCK);
      blockWithItem(ModBlocks.PROVIDER_BLOCK);
    }

    private <TYPE extends Block> void blockWithItem(RegistryObject<TYPE> blockRegistryObject) {
      simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
