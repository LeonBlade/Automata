package dev.leonblade.automata.common.network;

import dev.leonblade.automata.AutomataMod;
import dev.leonblade.automata.common.network.packet.ExamplePacketC2S;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

// import java.util.Optional;
import java.util.Optional;
import java.util.function.Function;

public class ModMessages {
  private static SimpleChannel channel;

  private static int packetId = 0;

  public static void register() {
    channel = NetworkRegistry.ChannelBuilder
      .named(ResourceLocation.fromNamespaceAndPath(AutomataMod.MOD_ID, "messages"))
      .networkProtocolVersion(() -> "1.0")
      .clientAcceptedVersions(s -> true)
      .serverAcceptedVersions(s -> true)
      .simpleChannel();

    registerClientToServer(ExamplePacketC2S.class, ExamplePacketC2S::decode);
  }

  private static <MSG extends IPacket> void registerClientToServer(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder) {
    registerMessage(type, decoder, NetworkDirection.PLAY_TO_SERVER);
  }

  private static <MSG extends IPacket> void registerServerToClient(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder) {
    registerMessage(type, decoder, NetworkDirection.PLAY_TO_CLIENT);
  }

  private static <MSG extends IPacket> void registerMessage(Class<MSG> type, Function<FriendlyByteBuf, MSG> decoder, NetworkDirection networkDirection) {
    channel.registerMessage(packetId++, type, IPacket::encode, decoder, IPacket::handle, Optional.of(networkDirection));
  }

  public static <MSG> void sendToServer(MSG message) {
    channel.sendToServer(message);
  }

  public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
    channel.send(PacketDistributor.PLAYER.with(() -> player), message);
  }

  public static <MSG> void sendToAllPlayers(MSG message) {
    channel.send(PacketDistributor.ALL.noArg(), message);
  }
}
