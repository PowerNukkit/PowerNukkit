package cn.nukkit.event.plugin;

import cn.nukkit.event.HandlerList;
import cn.nukkit.plugin.Plugin;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class PluginDisableEvent extends PluginEvent {

    private static final HandlerList handlers = new HandlerList();

    public PluginDisableEvent(Plugin plugin) {
        super(plugin);
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}
