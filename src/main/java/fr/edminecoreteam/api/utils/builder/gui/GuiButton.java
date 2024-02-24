package fr.edminecoreteam.api.utils.builder.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/*
 GUI BASED FROM https://github.com/Joupiter
 OPTIMISED BY GONOTAKU/GONPVP
 */

@Getter
@AllArgsConstructor
public class GuiButton {

    private final ItemStack itemStack;
    @Setter
    private Consumer<InventoryClickEvent> clickEvent;

    public GuiButton(final ItemStack itemStack) {
        this(itemStack, event -> event.setCancelled(true));
    }

}