package net.buycraft.plugin.bukkit.util.placeholder;

import net.buycraft.plugin.UuidUtil;
import net.buycraft.plugin.bukkit.BukkitBuycraftPlatformBase;
import net.buycraft.plugin.bukkit.BuycraftPluginBase;
import net.buycraft.plugin.data.QueuedCommand;
import net.buycraft.plugin.data.QueuedPlayer;
import net.buycraft.plugin.execution.placeholder.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.regex.Pattern;

public class BukkitNamePlaceholder implements Placeholder {
    private static final Pattern REPLACE_NAME = Pattern.compile("[{\\(<\\[](name|player|username)[}\\)>\\]]", Pattern.CASE_INSENSITIVE);

    @Override
    public String replace(String command, QueuedPlayer player, QueuedCommand queuedCommand) {
        if (player.getUuid() == null || player.getUuid().equals("")) {
            BukkitBuycraftPlatformBase platform = ((BuycraftPluginBase) Bukkit.getPluginManager().getPlugin("BuyCraftX")).getPlatform();
            return REPLACE_NAME.matcher(command).replaceAll(platform.getPlayer(player).getName());
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UuidUtil.mojangUuidToJavaUuid(player.getUuid()));
        if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
            return REPLACE_NAME.matcher(command).replaceAll(player.getName());
        }

        return REPLACE_NAME.matcher(command).replaceAll(offlinePlayer.getName());
    }
}
