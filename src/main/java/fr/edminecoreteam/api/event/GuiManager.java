package fr.edminecoreteam.api.event;

import fr.edminecoreteam.api.utils.builder.gui.Gui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 GUI BASED FROM https://github.com/Joupiter
 OPTIMISED BY GONOTAKU/GONPVP
 */

@Getter
public class GuiManager implements Listener {

    private final ConcurrentMap<UUID, Gui<?>> guis;

    public GuiManager(final Plugin plugin) {
        this.guis = new ConcurrentHashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::updateButtons, 20, 20);
    }

    public void open(final Player player, Gui<?> gui) {
        getGuis().put(player.getUniqueId(), gui);
        Optional.ofNullable(getGuis().get(player.getUniqueId())).ifPresent(menu -> menu.onOpen(player));
    }

    private void updateButtons() {
        getGuis().values().forEach(Gui::onUpdate);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        Optional.ofNullable(getGuis().get(e.getWhoClicked().getUniqueId()))
                .filter(gui -> e.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> {
                    if (e.getClick() == ClickType.SHIFT_RIGHT || e.getClick() == ClickType.SHIFT_LEFT) {
                        e.setCancelled(true);
                        return;
                    }
                    if (!e.getClickedInventory().equals(gui.getInventory())) return;
                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM)
                        gui.getButtons().entrySet().stream()
                                .filter(entry -> entry.getValue().getItemStack().hasItemMeta() && entry.getValue().getItemStack().getItemMeta().getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName()))
                                .findFirst().ifPresent(entry -> entry.getValue().getClickEvent().accept(e));
                    else
                        gui.getButtons().entrySet().stream()
                                .filter(entry -> entry.getValue().getItemStack().equals(e.getCurrentItem()))
                                .findFirst()
                                .ifPresent(entry -> entry.getValue().getClickEvent().accept(e));

                    e.setCancelled(true);
                });
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Optional.ofNullable(getGuis().get(e.getPlayer().getUniqueId()))
                .filter(gui -> e.getInventory().equals(gui.getInventory()))
                .filter(gui -> gui.getCloseConsumer() != null)
                .ifPresent(gui -> gui.getCloseConsumer().accept(e));
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Optional.ofNullable(getGuis().get(e.getWhoClicked().getUniqueId()))
                .filter(gui -> e.getInventory().equals(gui.getInventory()))
                .ifPresent(gui -> e.setCancelled(true));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        getGuis().remove(e.getPlayer().getUniqueId());
    }

}