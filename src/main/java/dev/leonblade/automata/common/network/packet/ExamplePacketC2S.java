package dev.leonblade.automata.common.network.packet;

import dev.leonblade.automata.common.entity.ProviderBlockEntity;
import dev.leonblade.automata.common.network.IPacket;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class ExamplePacketC2S implements IPacket {

  private final BlockPos blockPos;
  private final int index;

  public ExamplePacketC2S(BlockPos pos, int index) {
    this.blockPos = pos;
    this.index = index;
  }

  @Override
  public void handle(Context context) {
    var player = context.getSender();
    if (player != null) {
      context.enqueueWork(() -> {
        var blockEntity = WorldUtils.getTileEntity(player.level, this.blockPos);
        if (!(blockEntity instanceof ProviderBlockEntity providerBlockEntity)) {
          return;
        }
        switch (this.index) {
          case 0:
            providerBlockEntity.getThatShit();
            break;
          case 1:
            providerBlockEntity.addCount();
            break;
        }
      });
    }
  }

  @Override
  public void encode(FriendlyByteBuf buffer) {
    buffer.writeBlockPos(this.blockPos);
    buffer.writeInt(this.index);
  }

  public static ExamplePacketC2S decode(FriendlyByteBuf buffer) {
    return new ExamplePacketC2S(buffer.readBlockPos(), buffer.readInt());
  }
  
}
