package org.sweetiebelle.debugger;

import org.bukkit.plugin.java.JavaPlugin;

public class Debugger extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DebuggerListener(this), this);
    }

}
