package fr.edmine.api.utils.builders;

import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class JsonMessageBuilder
{
	
	private TextComponent textC;

	public JsonMessageBuilder(String message)
	{
		textC = new TextComponent(message);
	}

	public JsonMessageBuilder setText(String text)
	{
		textC.setText(text);
		return this;
	}

	public JsonMessageBuilder setClickEvent(ClickEvent clickEvent)
	{
		textC.setClickEvent(clickEvent);
		return this;
	}

	public JsonMessageBuilder setHoverEvent(HoverEvent hoverEvent)
	{
		textC.setHoverEvent(hoverEvent);
		return this;
	}

	public JsonMessageBuilder setBold(Boolean bold)
	{
		textC.setBold(bold);
		return this;
	}

	public JsonMessageBuilder setItalic(Boolean italic)
	{
		textC.setItalic(italic);
		return this;
	}

	public JsonMessageBuilder setColor(ChatColor chatColor)
	{
		textC.setColor(chatColor);
		return this;
	}

	public JsonMessageBuilder setUnderlined(Boolean underlined)
	{
		textC.setUnderlined(underlined);
		return this;
	}

	public JsonMessageBuilder setObfuscated(Boolean obfuscated)
	{
		textC.setObfuscated(obfuscated);
		return this;
	}

	public JsonMessageBuilder addExtra(String extra)
	{
		textC.addExtra(extra);
		return this;
	}

	public JsonMessageBuilder addExtra(BaseComponent baseComponent)
	{
		textC.addExtra(baseComponent);
		return this;
	}

	public JsonMessageBuilder setInsertion(String insertion)
	{
		textC.setInsertion(insertion);
		return this;
	}

	public JsonMessageBuilder setStrikethrough(Boolean strikethrough)
	{
		textC.setStrikethrough(strikethrough);
		return this;
	}

	// A FIX ICI
	public JsonMessageBuilder setExtra(List<BaseComponent> extras)
	{
		textC.setExtra(extras);
		return this;
	}

	public TextComponent build()
	{
		return textC;
	}

}
