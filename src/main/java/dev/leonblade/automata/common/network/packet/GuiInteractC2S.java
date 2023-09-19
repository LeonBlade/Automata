package dev.leonblade.automata.common.network.packet;

import dev.leonblade.automata.common.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class GuiInteractC2S implements IPacket {

  // the entity?
  // some property to define the interaction

  public GuiInteractC2S() {

  }

  @Override
  public void handle(NetworkEvent.Context context) {

  }

  @Override
  public void encode(FriendlyByteBuf buffer) {

  }
}
