package xyz.emirdev.emirutils.managers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.emirdev.emirutils.EmirUtils;
import xyz.emirdev.emirutils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ProxyIntegrationManager implements PluginMessageListener {
    private final Map<String, Integer> playerCounts = new HashMap<>();
    private final Map<String, Boolean> serverStatus = new HashMap<>();

    public ProxyIntegrationManager() {
        EmirUtils.get().getServer().getMessenger().registerIncomingPluginChannel(
                EmirUtils.get(),
                "emirutilsvelocity:serverstatus",
                this
        );
        EmirUtils.get().getServer().getMessenger().registerIncomingPluginChannel(
                EmirUtils.get(),
                "emirutilsvelocity:serverplayercount",
                this
        );
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput data = ByteStreams.newDataInput(message);

        if (channel.equals("emirutilsvelocity:serverstatus")) {
            String server = data.readUTF();
            boolean online = data.readBoolean();
            serverStatus.put(server, online);

        } else if (channel.equals("emirutilsvelocity:serverplayercount")) {
            String server = data.readUTF();
            int playerCount = data.readInt();
            playerCounts.put(server, playerCount);
        }
    }

    public boolean isServerOnline(String server) {
        return serverStatus.getOrDefault(server, false);
    }

    public int getPlayerCount(String server) {
        return playerCounts.getOrDefault(server, 0);
    }
}
