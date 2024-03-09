package fr.edmine.api.message;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Message
{
	private ChannelType channelType;
	private Player player;
	private String prefix = "";
	private String permission;
	
	public Message(ChannelType channelType)
	{
		this.channelType = channelType;
	}
	
	public Message(Player player)
	{
		this.player = player;
	}
	
	public void send(String message)
	{
		switch (this.channelType)
		{
			case CONSOLE:
				Bukkit.getServer().getConsoleSender().sendMessage(this.prefix + message);
				break;
				
			case BROADCAST:
				for (Player player : Bukkit.getServer().getOnlinePlayers()) player.sendMessage(this.prefix + message);
				break;
		}
		if (this.player != null) this.player.sendMessage(this.prefix + message);
		
		if (this.permission != null && this.player.hasPermission(this.permission)) this.player.sendMessage(this.prefix + message);
		else this.send(this.prefix + "§7Vous n'avez pas la permission");
	}
	
	public void hasPermission(String permission)
	{
		this.permission = permission;
	}
	
	public void setPrefix(final String prefix)
	{
		this.prefix = prefix;
	}
	
	public enum ChannelType
	{
		CONSOLE,
		BROADCAST
	}
}
