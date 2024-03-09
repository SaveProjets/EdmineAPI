package fr.edmine.api.utils;

public class ServerType
{
	public static boolean isBungeeCordServer() throws ClassNotFoundException
	{
		return Class.forName("net.md_5.bungee.api.ProxyServer") != null ? true : false;
	}

	public static boolean isSpigotServer() throws ClassNotFoundException
	{
		return Class.forName("org.bukkit.Bukkit") != null ? true : false;
	}
}
