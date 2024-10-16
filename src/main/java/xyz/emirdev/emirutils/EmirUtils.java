package xyz.emirdev.emirutils;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitLamp;
import xyz.emirdev.emirutils.commands.*;

public final class EmirUtils extends JavaPlugin {
    private static EmirUtils instance;
    private ConfigHandler config;
    private static LuckPerms luckPerms;

    public ConfigHandler getPluginConfig() {
        return config;
    }

    public static EmirUtils get() {
        return instance;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigHandler();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        luckPerms = provider.getProvider();

        var lamp = BukkitLamp.builder(this).build();
        lamp.register(new KickCommand());
        lamp.register(new BanCommand());
        lamp.register(new TempBanCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
