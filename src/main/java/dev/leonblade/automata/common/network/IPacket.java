package dev.leonblade.automata.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public interface IPacket {
  void handle(NetworkEvent.Context context);
  void encode(FriendlyByteBuf buffer);

  static <P extends IPacket> void handle(P message, Supplier<NetworkEvent.Context> context) {
    if (message != null) {
      var ctx = context.get();
      ctx.enqueueWork(() -> message.handle(ctx));
      ctx.setPacketHandled(true);
    }
  }
}
