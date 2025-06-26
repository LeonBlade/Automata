package dev.leonblade.automata;

import dev.leonblade.automata.common.block.ModBlocks;
import com.mojang.logging.LogUtils;
import dev.leonblade.automata.common.entity.ModBlockEntities;
import dev.leonblade.automata.client.gui.ProviderScreen;
import dev.leonblade.automata.common.item.ModCreativeModeTab;
import dev.leonblade.automata.common.item.ModItems;
import dev.leonblade.automata.common.network.ModMessages;
import dev.leonblade.automata.client.gui.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AutomataMod.MOD_ID)
public class AutomataMod {
  public static final String MOD_ID = "automata";
  private static final Logger LOGGER = LogUtils.getLogger();

  public AutomataMod(FMLJavaModLoadingContext context) {
    var eventBus = context.getModEventBus();

    // Register the setup method for mod loading
    eventBus.addListener(this::setup);
    // Register the enqueueIMC method for mod loading
    eventBus.addListener(this::enqueueIMC);
    // Register the processIMC method for mod loading
    eventBus.addListener(this::processIMC);

    // Register our stuff
    ModItems.register(eventBus);
    ModBlocks.register(eventBus);
    ModBlockEntities.register(eventBus);
    ModMenuTypes.register(eventBus);
    ModCreativeModeTab.register(eventBus);
    ModMessages.register();

    MinecraftForge.EVENT_BUS.register(this);

    context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void setup(final FMLCommonSetupEvent event) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP");

    if (Config.goated)
      LOGGER.info("GOATED STATUS SPOTTED!");
  }

  private void enqueueIMC(final InterModEnqueueEvent event) {
    // Some example code to dispatch IMC to another mod
    InterModComms.sendTo(MOD_ID, "helloworld", () -> {
      LOGGER.info("Hello world from the MDK");
      return "Hello world";
    });
  }

  private void processIMC(final InterModProcessEvent event) {
    // Some example code to receive and process InterModComms from other mods
    LOGGER.info("Got IMC {}", event.getIMCStream()
        .map(m -> m.messageSupplier().get())
        .collect(Collectors.toList()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP");
      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

      MenuScreens.register(ModMenuTypes.PROVIDER_MENU.get(), ProviderScreen::new);
    }
  }
}
