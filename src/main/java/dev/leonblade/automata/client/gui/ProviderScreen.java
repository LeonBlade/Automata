package dev.leonblade.automata.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import dev.leonblade.automata.common.network.ModMessages;
import dev.leonblade.automata.common.network.packet.ExamplePacketC2S;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ProviderScreen extends AbstractContainerScreen<ProviderMenu> implements ContainerEventHandler {
  public ProviderScreen(ProviderMenu menu, Inventory inventory, Component title) {
    super(menu, inventory, title);
  }

  @Override
  protected void init() {
    super.init();

    var wX = (width - imageWidth) / 2;
    var wY = (height - imageHeight) / 2;

    this.addRenderableWidget(new Button(wX + 8, wY + 48, 50, 20, Component.literal("Drink Me"), this::onPress));
    this.addRenderableWidget(new Button(wX + 64, wY + 48, 16, 20, Component.literal("+"), this::onAdd));
  }

  private void onPress(Button button) {
    LogUtils.getLogger().info("IM A BUTTON AND IM PRESSED");
    ModMessages.sendToServer(new ExamplePacketC2S(menu.blockEntity.getBlockPos(), 0));
  }

  private void onAdd(Button button) {
    ModMessages.sendToServer(new ExamplePacketC2S(menu.blockEntity.getBlockPos(), 1));
  }

  @Override
  protected void renderBg(@NotNull PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
    var x = (width - imageWidth) / 2;
    var y = (height - imageHeight) / 2;

    var slots = this.menu.slots;

    fill(stack, x, y, x + imageWidth, y + imageHeight, 0xffffffff);

    for (var slot : slots) {
      var sx = slot.x;
      var sy = slot.y;
      fill(stack, x + sx, y + sy, x + sx + 16, y + sy + 16, 0x33000000);
    }

    drawCenteredString(stack, this.getMinecraft().font, Component.literal(Integer.toString(this.menu.data.get(0))), x + 48, y + 22, 0xFF0066FF);
  }

  @Override
  public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
    renderBackground(stack);
    super.render(stack, mouseX, mouseY, delta);
    renderTooltip(stack, mouseX, mouseY);
  }
}
