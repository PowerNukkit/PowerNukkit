package cn.nukkit.command;

import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import lombok.NonNull;

@PowerNukkitOnly @PowerNukkitDifference(info = "An easiest way to register commands on PowerNukkit")

/**
 * @apiNote Works only on newer PowerNukkit versions.
 * @implSpec To implement this on your plugin, create a new Instance (by "new CommandRegistrator(this, commandName)"), and, set an executor by "setExecutor(CommandExecutor)", and register, using the "register()" void method.
 * @example new CommandRegistrator(this, "vanish").setExecutor(new VanishCommand()).register();
 * */

public class CommandRegistrator {
    private Plugin plugin;
    private String name;
    private CommandExecutor executor;

    public CommandRegistrator(@NonNull Plugin plugin, @NonNull String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public CommandRegistrator setExecutor(@NonNull CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public void register() {
        assert executor != null;
        assert name != null;
        assert plugin != null;

        PluginBase pluginBase = (PluginBase) plugin;
        PluginCommand<?> pluginCommand = (PluginCommand<?>) pluginBase.getCommand(name);
        pluginCommand.setExecutor(executor);
    }
}
