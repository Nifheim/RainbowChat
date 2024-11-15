package com.github.beelzebu.rainbowchat.storage;

import com.github.beelzebu.rainbowchat.channel.ChatChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.kyori.adventure.identity.Identified;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class MemoryChatStorage implements ChatStorage {

    private final Map<String, ChatChannel> channelMap = new HashMap<>();
    private final Map<UUID, String> playerMap = new HashMap<>();
    private ChatChannel defChannel;

    @Override
    public @NotNull ChatChannel getChannel(@NotNull Player player) {
        String channelName = playerMap.get(player.getUniqueId());
        if (channelName != null) {
            ChatChannel chatChannel = channelMap.get(channelName);
            if (chatChannel != null) {
                return chatChannel;
            }
        }
        return defChannel;
    }

    @Override
    public @NotNull ChatChannel getChannelByName(String channel) {
        return channelMap.get(channel);
    }

    @Override
    public @NotNull ChatChannel getDefaultChannel() {
        return defChannel;
    }

    @Override
    public void addToChannel(Identified identified, @Nullable ChatChannel chatChannel) {
        if (chatChannel == null) {
            playerMap.remove(identified.identity().uuid());
        } else {
            playerMap.put(identified.identity().uuid(), chatChannel.getName());
        }
    }

    @Override
    public void resetChannel(Identified identified) {
        playerMap.remove(identified.identity().uuid());
    }

    @Override
    public void loadChannel(@NotNull ChatChannel chatChannel) {
        channelMap.put(chatChannel.getName(), chatChannel);
        if (chatChannel.isDefault()) {
            defChannel = chatChannel;
        }
    }

    @Override
    public @NotNull Collection<ChatChannel> getChannels() {
        return channelMap.values();
    }

    @Override
    public void clearChannels() {
        playerMap.clear();
        channelMap.clear();
    }
}
