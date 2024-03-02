package fr.edminecoreteam.api.utils.builder.gui;

import fr.edminecoreteam.api.EdmineAPISpigot;
import fr.edminecoreteam.api.utils.builder.gui.GuiButton;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Getter
@Setter
public abstract class Gui<P extends JavaPlugin> {

    private final P plugin;

    private Inventory inventory;
    private final String inventoryName;
    private final int rows;

    private final ConcurrentMap<Integer, GuiButton> buttons;
    private Consumer<InventoryCloseEvent> closeConsumer;

    public Gui(final P plugin, final String inventoryName, final int rows) {
        this.plugin = plugin;
        this.inventoryName = inventoryName;
        this.rows = rows;
        this.buttons = new ConcurrentHashMap<>();
        this.inventory = Bukkit.createInventory(null, rows * 9, inventoryName);
        setCloseInventory(event -> {
            onEnd();
            EdmineAPISpigot.getInstance().getGuiManager().getGuis().remove(event.getPlayer().getUniqueId());
        });
    }

    public void onEnd() {}
    public abstract void setup();

    public void onOpen(final Player player) {
        setup();
        player.openInventory(getInventory());
    }

    public void onUpdate() {}

    public void close(final Player player) {
        player.closeInventory();
    }

    public void fillAllInventory(GuiButton button) {
        IntStream.range(0, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public void fillAllInventory(ItemStack button) {
        IntStream.range(0, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public void fillAllInventory(GuiButton button, int minimumSlot) {
        IntStream.range(minimumSlot, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public void fillAllInventory(ItemStack button, int minimumSlot) {
        IntStream.range(minimumSlot, getSize())
                .filter(i -> getItem(i) != null)
                .forEach(i -> setItem(i, button));
    }

    public void setItem(final int slot, final ItemStack itemstack) {
        getInventory().setItem(slot, itemstack);
    }

    public void setItem(final int slot, final GuiButton button) {
        getButtons().put(slot, button);
        getInventory().setItem(slot, button.getItemStack());
    }

    public void setItems(final int[] slots, final GuiButton button) {
        for (int slot : slots)
            setItem(slot, button);
    }

    public void setItems(final int[] slots, final ItemStack itemStack) {
        for (int slot : slots)
            setItem(slot, itemStack);
    }

    public void setHorizontalLine(final int from, final int to, final GuiButton button) {
        IntStream.range(from, to + 1)
                .forEach(slot -> setItem(slot, button));
    }

    public void setHorizontalLine(final int from, final int to, final ItemStack item) {
        setHorizontalLine(from, to, new GuiButton(item));
    }

    public void setVerticalLine(final int from, final int to, final GuiButton button) {
        int slot = from;
        while (slot <= to) {
            setItem(slot, button);
            slot += 9;
        }
    }

    public void setVerticalLine(final int from, final int to, final ItemStack item) {
        setVerticalLine(from, to, new GuiButton(item));
    }

    public void addItem(final GuiButton item) {
        setItem(getInventory().firstEmpty(), item);
    }

    public int[] getBorders() {
        return IntStream.range(0, getSize())
                .filter(i -> getSize() < 27 || i < 9 || i % 9 == 0 || (i - 8) % 9 == 0 || i > getSize() - 9)
                .parallel()
                .toArray();
    }

    public void removeItem(final int slot) {
        getButtons().remove(slot);
        getInventory().remove(getInventory().getItem(slot));
    }

    public void removeAction(final int slot) {
        getButtons().remove(slot);
    }

    public void clear() {
        getButtons().keySet().forEach(this::removeItem);
        getButtons().forEach((slot, item) -> getInventory().setItem(slot, item.getItemStack()));
    }

    public void refresh() {
        clear();
        setup();
    }

    public GuiButton getItem(final int slot) {
        return getButtons().get(slot);
    }

    public int getSize() {
        return rows * 9;
    }

    public void setCloseInventory(Consumer<InventoryCloseEvent> closeConsumer) {
        this.closeConsumer = closeConsumer;
    }

}