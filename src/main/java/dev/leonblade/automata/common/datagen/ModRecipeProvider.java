package dev.leonblade.automata.common.datagen;

import dev.leonblade.automata.AutomataMod;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
  // private static final List<ItemLike> ITEMS = List.of(ModItems.ITEM.get())

  public ModRecipeProvider(PackOutput pOutput) {
    super(pOutput);
  }

  @Override
  protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
    // oreBlasting(pWriter, ITEMS, RecipeCategory.MISC, ModItems.ITEM.get(), 0.0f, 100, "group");

    // ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ITEM.get())
    //     .pattern("ABC")
    //     .pattern("ABC")
    //     .pattern("ABC")
    //     .define("A", ModItems.ITEM.get())
    //     .unlockedBy(getHasName(ModItems.ITEM.get()), has(ModItems.ITEM.get()))
    //     .save(pWriter);

    // ShapelessRecipe.shapeless(RecipeCategory.MISC, ModItems.ITEM.get(), 4)
    //     .requires(ModItems.ITEM.get())
    //     .unlockedBy(getHasName(ModItems.ITEM.get()), has(ModItems.ITEM.get()))
    //     .save(pWriter);
  }

  protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTIme, @NotNull String pGroup) {
    oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
  }

  protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
    oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
  }

  protected static void oreCooking(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
    for(ItemLike itemlike : pIngredients) {
      SimpleCookingRecipeBuilder.generic(
          Ingredient.of(itemlike),
          pCategory,
          pResult,
          pExperience,
          pCookingTime,
          pCookingSerializer)
          .group(pGroup)
          .unlockedBy(getHasName(itemlike), has(itemlike))
          .save(pFinishedRecipeConsumer, AutomataMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
    }
  }
}
