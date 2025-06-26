package dev.leonblade.automata;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = AutomataMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
  private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

  private static final ForgeConfigSpec.BooleanValue GOATED = BUILDER
      .comment("Are you goated?")
      .define("goated", true);

  static final ForgeConfigSpec SPEC = BUILDER.build();

  public static boolean goated;

  @SubscribeEvent
  static void onLoad(final ModConfigEvent event) {
    goated = GOATED.get();
  }
}
