package org.sweetiebelle.debugger;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.RegisteredListener;

public class DebuggerListener implements Listener {

    private Debugger plugin;

    public DebuggerListener(Debugger plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void lowestPlayerPortal(PlayerPortalEvent event) {
        dumpPlayerMoveEvent(event, EventPriority.LOWEST);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void monitorPlayerPortal(PlayerPortalEvent event) {
        dumpPlayerMoveEvent(event, EventPriority.MONITOR);
        dumpEventHandlers(event);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    public void lowestPlayerTeleport(PlayerTeleportEvent event) {
        dumpPlayerMoveEvent(event, EventPriority.LOWEST);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void monitorPlayerTeleport(PlayerTeleportEvent event) {
        dumpPlayerMoveEvent(event, EventPriority.MONITOR);
        dumpEventHandlers(event);
    }

    private void dumpEventHandlers(Event event) {
        if (event instanceof Cancellable && ((Cancellable) event).isCancelled()) {
            plugin.getLogger().severe("WARNING: A " + event.getClass().getSimpleName() + " is about to be cancelled.");
            plugin.getLogger().severe("Here is the call chain:");
            for (RegisteredListener listener : event.getHandlers().getRegisteredListeners()) {
                plugin.getLogger().severe("- " + listener.getPlugin().getName() + " (PRIORITY " + listener.getPriority() + ")");
            }
        } else {
            plugin.getLogger().info("A " + event.getClass().getSimpleName() + " has fired successfully.");
        }
    }

    private <T extends PlayerMoveEvent> void dumpPlayerMoveEvent(T event, EventPriority priority) {
        plugin.getLogger().info("Here's some information about this " + event.getClass().getSimpleName() + "(hash=" + event.hashCode() + "). This is a dump with the priority of " + priority.toString());
        plugin.getLogger().info("This event is associated with " + event.getPlayer().getName());
        plugin.getLogger().info("It's from location is " + event.getFrom().toString());
        plugin.getLogger().info("It's to location is " + event.getTo().toString());
    }
}
