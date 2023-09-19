package dev.leonblade.automata;

import dev.leonblade.automata.common.block.ModBlocks;
import com.mojang.logging.LogUtils;
import dev.leonblade.automata.common.entity.ModBlockEntities;
import dev.leonblade.automata.client.gui.ProviderScreen;
import dev.leonblade.automata.common.item.ModItems;
import dev.leonblade.automata.common.network.ModMessages;
import dev.leonblade.automata.client.gui.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AutomataMod.MOD_ID)
public class AutomataMod {
  public static final String MOD_ID = "automata";

  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  public AutomataMod() {
    var eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // Register the setup method for modloading
    eventBus.addListener(this::setup);
    // Register the enqueueIMC method for modloading
    eventBus.addListener(this::enqueueIMC);
    // Register the processIMC method for modloading
    eventBus.addListener(this::processIMC);

    // Register our stuff
    ModItems.register(eventBus);
    ModBlocks.register(eventBus);
    ModBlockEntities.register(eventBus);
    ModMenuTypes.register(eventBus);
    ModMessages.register();

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event) {
    // some preinit code
    LOGGER.info("HELLO FROM PREINIT");
    LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT).getPath());
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
    LOGGER.info("Got IMC {}", event.getIMCStream().
            map(m -> m.messageSupplier().get()).
            collect(Collectors.toList()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      MenuScreens.register(ModMenuTypes.PROVIDER_MENU.get(), ProviderScreen::new);
    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegisterEvent event) {
      event.register(ForgeRegistries.Keys.BLOCKS, helper -> {
        LOGGER.info("HELLO from Register Block " + helper.toString());
      });
    }

    // @SubscribeEvent
    // public static void onAttachingCapabilities(final @NotNull AttachCapabilitiesEvent<ItemStack> event) {
    //   if (event.getObject().getItem() == POGGER_ITEM.get()) {
    //     event.addCapability(new ResourceLocation(MOD_ID, "poggers"), new TestProvider());
    //   }
    // }
  }
}
