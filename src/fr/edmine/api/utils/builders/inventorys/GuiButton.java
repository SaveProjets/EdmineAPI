package fr.edmine.api.utils.builders.inventorys;

import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/*
 * GUI BASED FROM https://github.com/Joupiter OPTIMISED BY GONOTAKU/GONPVP
 */

public class GuiButton
{

	private final ItemStack itemStack;
	private Consumer<InventoryClickEvent> clickEvent;
	
	/*
	public GuiButton(final ItemStack itemStack)
	{	
		this(itemStack, event -> event.setCancelled(true));
	}
	*/
	
	public GuiButton(final ItemStack itemStack)
	{
        this.itemStack = itemStack;
        this.clickEvent = event -> event.setCancelled(true);
    }
	
	public GuiButton(final ItemStack itemStack, Consumer<InventoryClickEvent> clickEvent)
	{
        this.itemStack = itemStack;
        this.clickEvent = clickEvent;
    }
	
	public ItemStack getItemStack()
	{
		return this.itemStack;
	}

	public void setClickEvent(Consumer<InventoryClickEvent> clickEvent)
	{
		this.clickEvent = clickEvent;
	}

	public Consumer<InventoryClickEvent> getClickEvent()
	{
		return clickEvent;
	}
}