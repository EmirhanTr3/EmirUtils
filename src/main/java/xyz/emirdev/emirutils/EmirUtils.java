package xyz.emirdev.emirutils;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitLamp;
import xyz.emirdev.emirutils.commands.*;
import xyz.emirdev.emirutils.events.PlayerChatEvent;
import xyz.emirdev.emirutils.handlers.ConfigHandler;
import xyz.emirdev.emirutils.handlers.DataHandler;
import xyz.emirdev.emirutils.managers.ProxyIntegrationManager;
import xyz.emirdev.emirutils.punishutils.PunishDuration;
import xyz.emirdev.emirutils.parameters.OfflinePlayerParameterType;
import xyz.emirdev.emirutils.punishutils.PunishReason;
import xyz.emirdev.emirutils.parameters.PunishDurationParameterType;
import xyz.emirdev.emirutils.parameters.PunishReasonParameterType;
import xyz.xenondevs.invui.InvUI;

import java.util.List;

public final class EmirUtils extends JavaPlugin {
    private static EmirUtils instance;
    private ConfigHandler config;
    private DataHandler data;
    private static LuckPerms luckPerms;
    private ProxyIntegrationManager proxyIntegrationManager;

    public ConfigHandler getPluginConfig() {
        return config;
    }

    public DataHandler getData() {
        return data;
    }

    public static EmirUtils get() {
        return instance;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public ProxyIntegrationManager getProxyIntegrationManager() {
        return proxyIntegrationManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigHandler();
        data = new DataHandler();
        InvUI.getInstance().setPlugin(this);
        proxyIntegrationManager = new ProxyIntegrationManager();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        luckPerms = provider.getProvider();

        var lamp = BukkitLamp.builder(this)
                .parameterTypes(builder -> {
                    builder.addParameterType(OfflinePlayer.class, new OfflinePlayerParameterType());
                    builder.addParameterType(PunishReason.class, new PunishReasonParameterType());
                    builder.addParameterType(PunishDuration.class, new PunishDurationParameterType());
                })
                .build();

        List.of(
                new KickCommand(),
                new BanCommand(),
                new TempBanCommand(),
                new UnbanCommand(),
                new MuteCommand(),
                new UnmuteCommand(),
                new HistoryCommand()
        ).forEach(lamp::register);

        List.of(
                new PlayerChatEvent()
        ).forEach(e -> Bukkit.getPluginManager().registerEvents(e, this));

    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
