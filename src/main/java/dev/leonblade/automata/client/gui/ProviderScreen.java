package dev.leonblade.automata.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.network.ModMessages;
import dev.leonblade.automata.common.network.packet.ExamplePacketC2S;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ProviderScreen extends AbstractContainerScreen<ProviderMenu> implements ContainerEventHandler {
  private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(AutomataMod.MOD_ID, "textures/gui/provider/provider_gui.png");

  public ProviderScreen(ProviderMenu menu, Inventory inventory, Component title) {
    super(menu, inventory, title);
  }

  @Override
  protected void init() {
    super.init();

    this.titleLabelX = 30;
    this.titleLabelY = 8;

    var wX = (width - imageWidth) / 2;
    var wY = (height - imageHeight) / 2;

    var extractButton = Button.builder(Component.literal("Extract"), this::onPress)
        .bounds(wX + 87, wY + 6, 60, 20)
        .build();

    var addButton = Button.builder(Component.literal("+"), this::onAdd)
        .bounds(wX + 150, wY + 29, 20, 20)
        .build();

    var removeButton = Button.builder(Component.literal("-"), this::onRemove)
        .bounds(wX + 150, wY + 50, 20, 20)
        .build();

    this.addRenderableWidget(extractButton);
    this.addRenderableWidget(addButton);
    this.addRenderableWidget(removeButton);
  }

  private void onPress(Button button) {
    LogUtils.getLogger().info("I'M A BUTTON AND IM PRESSED");
    ModMessages.sendToServer(new ExamplePacketC2S(menu.blockEntity.getBlockPos(), 0, 0));
  }

  private void onAdd(Button button) {
    ModMessages.sendToServer(new ExamplePacketC2S(menu.blockEntity.getBlockPos(), 1, 1));
  }

  private void onRemove(Button button) {
    ModMessages.sendToServer(new ExamplePacketC2S((menu.blockEntity.getBlockPos()), 1, -1));
  }

  @Override
  protected void renderBg(GuiGraphics gfx, float pPartialTick, int pMouseX, int pMouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.f, 1.f, 1.f, 1.f);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);

    var x = (width - imageWidth) / 2;
    var y = (height - imageHeight) / 2;

    gfx.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

    gfx.drawCenteredString(this.getMinecraft().font, Component.literal(Integer.toString(this.menu.data.get(0))), x + 32, y + 30, 0xFFFFFF);
  }

  @Override
  public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
    renderBackground(gfx);
    super.render(gfx, mouseX, mouseY, delta);
    renderTooltip(gfx, mouseX, mouseY);
  }
}
