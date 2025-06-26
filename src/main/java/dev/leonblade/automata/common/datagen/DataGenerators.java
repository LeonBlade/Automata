package dev.leonblade.automata.common.datagen;

import dev.leonblade.automata.AutomataMod;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AutomataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    var generator = event.getGenerator();
    var packOutput = generator.getPackOutput();
    var existingFileHelper = event.getExistingFileHelper();

    generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));

    generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
    generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
  }
}
