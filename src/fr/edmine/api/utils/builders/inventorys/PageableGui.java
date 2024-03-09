package fr.edmine.api.utils.builders.inventorys;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * GUI BASED FROM https://github.com/Joupiter OPTIMISED BY GONOTAKU/GONPVP
 */

@SuppressWarnings("unused")
public abstract class PageableGui<P extends JavaPlugin, E> extends Gui<P>
{

	private final int maxItems;

	private final Pagination<E> pagination;
	private Pagination<E>.Page page;

	protected PageableGui(final P plugin,final String inventoryName,final int rows,final int maxItems)
	{
		super(plugin, inventoryName, rows);
		this.maxItems = maxItems;
		this.pagination = new Pagination<>(getMaxItems());
		this.page = pagination.getPage(1);
	}

	public abstract GuiButton nextPageButton();

	public abstract GuiButton previousPageButton();

	public void updatePage(final Pagination<E>.Page page)
	{
		setPage(page);
		refresh();
	}

	public void updatePage2(final Pagination<E>.Page page)
	{
		setPage(page);
	}

	private void setPage(Pagination<E>.Page page)
	{
		this.page = page;
	}
	
	private int getMaxItems()
	{
		return this.maxItems;
	}
}
