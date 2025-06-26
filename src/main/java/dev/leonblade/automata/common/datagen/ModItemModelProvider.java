package dev.leonblade.automata.common.datagen;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
  public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, AutomataMod.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleItem(ModItems.TEST_ITEM);
    simpleItem(ModItems.POGGER_ITEM);
  }

  private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
    assert item.getId() != null;
    return withExistingParent(item.getId().getPath(),
        ResourceLocation.parse("item/generated")).texture("layer0",
        ResourceLocation.fromNamespaceAndPath(AutomataMod.MOD_ID, "item/" + item.getId().getPath()));
  }
}
