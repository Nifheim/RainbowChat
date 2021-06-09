package com.github.beelzebu.rainbowchat.storage;

import com.github.beelzebu.rainbowchat.channel.ChatChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Beelzebu
 */
public class MemoryChatStorage implements ChatStorage {

    private final Map<String, ChatChannel> channelMap = new HashMap<>();
    private final Map<UUID, String> playerMap = new HashMap<>();

    @Override
    public @NotNull ChatChannel getChannel(@NotNull Player player) {
        String channelName = playerMap.get(player.getUniqueId());
        if (channelName != null) {
            ChatChannel chatChannel = channelMap.get(channelName);
            if (chatChannel != null) {
                return chatChannel;
            }
        }
        return channelMap.values().stream().filter(ChatChannel::isDefault).findAny().orElse(null);
    }

    @Override
    public void setChannel(UUID uniqueId, @Nullable ChatChannel chatChannel) {
        if (chatChannel == null) {
            playerMap.remove(uniqueId);
        } else {
            playerMap.put(uniqueId, chatChannel.getName());
        }
    }

    @Override
    public void loadChannel(@NotNull ChatChannel chatChannel) {
        channelMap.put(chatChannel.getName(), chatChannel);
    }

    @Override
    public @NotNull Collection<ChatChannel> getChannels() {
        return channelMap.values();
    }

    @Override
    public void clearChannels() {
        channelMap.clear();
    }
}