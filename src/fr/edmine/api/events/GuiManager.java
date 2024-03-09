package fr.edmine.api.events;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
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

import fr.edmine.api.utils.builders.inventorys.Gui;
import fr.edmine.api.utils.builders.inventorys.GuiButton;

/*
 * GUI BASED FROM https://github.com/Joupiter OPTIMISED BY GONOTAKU/GONPVP
 */

public class GuiManager implements Listener
{

	private final ConcurrentMap<UUID, Gui<?>> guis;

	public GuiManager(final Plugin plugin)
	{	
		this.guis = new ConcurrentHashMap<>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::updateButtons, 20, 20);
	}

	public void open(final Player player, Gui<?> gui)
	{
		getGuis().put(player.getUniqueId(), gui);
		Optional.ofNullable(getGuis().get(player.getUniqueId())).ifPresent(menu -> menu.onOpen(player));
	}

	private void updateButtons()
	{
		getGuis().values().forEach(Gui::onUpdate);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onClick(InventoryClickEvent event)
	{
		if (event.getCurrentItem() != null) Optional.ofNullable(getGuis().get(event.getWhoClicked().getUniqueId())).ifPresent(gui ->
		{
			if (!event.getClickedInventory().equals(gui.getInventory())) return;

			event.setCancelled(true);
			if (event.getClick() == ClickType.SHIFT_RIGHT || event.getClick() == ClickType.SHIFT_LEFT)
			{
				return;
			}
			final GuiButton guiButton = gui.getButtons().get(event.getSlot());
			if (guiButton != null)
			{
				guiButton.getClickEvent().accept(event);
			}
		});
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event)
	{
		Optional.ofNullable(getGuis().get(event.getPlayer().getUniqueId())).filter(gui -> event.getInventory().equals(gui.getInventory())).filter(gui -> gui.getCloseConsumer() != null).ifPresent(gui -> gui.getCloseConsumer().accept(event));
	}

	@EventHandler
	public void onDrag(InventoryDragEvent event)
	{
		Optional.ofNullable(getGuis().get(event.getWhoClicked().getUniqueId())).filter(gui -> event.getInventory().equals(gui.getInventory())).ifPresent(gui -> event.setCancelled(true));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event)
	{
		getGuis().remove(event.getPlayer().getUniqueId());
	}

	public ConcurrentMap<UUID, Gui<?>> getGuis()
	{
		return guis;
	}
	
}