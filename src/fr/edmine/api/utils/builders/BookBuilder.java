package fr.edmine.api.utils.builders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookBuilder
{

	private final String title;
	private final String author;
	private final List<String> pages;

	public BookBuilder(String title,String author)
	{
		this.title = title;
		this.author = author;
		this.pages = new ArrayList<>();
	}

	public BookBuilder addPage(String pageContent)
	{
		pages.add(pageContent);
		return this;
	}

	public BookBuilder addEmptyPage()
	{
		pages.add("");
		return this;
	}

	public BookBuilder setPage(int pageIndex, String pageContent)
	{
		if (pageIndex >= 0 && pageIndex < pages.size())
		{
			pages.set(pageIndex, pageContent);
		}
		else
		{
			pages.add(pageContent);
		}
		return this;
	}

	public ItemStack build()
	{
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();

		bookMeta.setTitle(title);
		bookMeta.setAuthor(author);
		bookMeta.setPages(pages);

		book.setItemMeta(bookMeta);
		return book;
	}

	public String generateBookSummary()
	{
		StringBuilder summary = new StringBuilder("Titre: " + title + "\nAuteur: " + author + "\n\n");
		int pageCount = 1;
		for (String page : pages)
		{
			summary.append("Page ").append(pageCount).append(":\n").append(page).append("\n\n");
			pageCount++;
		}
		return summary.toString();
	}

}
