package fr.edminecoreteam.api.utils.builder.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

/*
 GUI BASED FROM https://github.com/Joupiter
 OPTIMISED BY GONOTAKU/GONPVP
 */

@Getter
public abstract class PageableGui<P extends JavaPlugin, E> extends Gui<P> {

    private final int maxItems;

    private final Pagination<E> pagination;
    @Setter private Pagination<E>.Page page;

    protected PageableGui(final P plugin, final String inventoryName, final int rows, final int maxItems) {
        super(plugin, inventoryName, rows);
        this.maxItems = maxItems;
        this.pagination = new Pagination<>(getMaxItems());
        this.page = pagination.getPage(1);
    }

    public abstract GuiButton nextPageButton();

    public abstract GuiButton previousPageButton();

    public void updatePage(final Pagination<E>.Page page) {
        setPage(page);
        refresh();
    }

    public void updatePage2(final Pagination<E>.Page page) {
        setPage(page);
    }

}
