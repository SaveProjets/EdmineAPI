package fr.edmine.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.edmine.api.database.DatabaseManager;
import fr.edmine.api.events.BossBarEvent;
import fr.edmine.api.events.GuiManager;
import fr.edmine.api.events.PlayerEvents;
import fr.edmine.api.managers.WorldManager;
import fr.edmine.api.message.Message;
import fr.edmine.api.message.Message.ChannelType;
import fr.edmine.api.utils.DatabaseUtils;
import fr.edmine.api.utils.builders.BossBarBuilder;
import fr.edmine.api.utils.builders.HologramsBuilder;
import fr.minuskube.inv.InventoryManager;

public class EdmineAPISpigot extends JavaPlugin
{
	private static EdmineAPISpigot instance;
	private HologramsBuilder hologramsBuilder;
	private WorldManager worldManager;
	private BossBarBuilder bossBar;

	private DatabaseUtils databaseUtils;
	private final InventoryManager smartInvManager = new InventoryManager(this);

	private GuiManager guiManager;
	private Message message = new Message(ChannelType.CONSOLE);
	

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		String prefix = "§8[§7EdmineApi§8] §r";
		
		this.message.send(prefix + "§b- §aenabling");
		this.smartInvManager.init();
		instance = this;

		this.message.send(prefix + "§7Loading managers...");
		
		this.hologramsBuilder = new HologramsBuilder();
		this.bossBar = new BossBarBuilder("§rEdmine Network", 300);
		this.databaseUtils = new DatabaseUtils();
		this.worldManager = new WorldManager();
		this.guiManager = new GuiManager(this);
		
		this.registerCommands();
		this.registerListeners();
		
		this.message.send(prefix + "§7Managers loaded successfully.");
		DatabaseManager.initAllDatabaseConnection();
	}

	private void registerCommands()
	{
		//this.getCommand("command").setExecutor(new Command());
	}
	
	private void registerListeners()
	{
		PluginManager pluginManager = Bukkit.getPluginManager();
		
		pluginManager.registerEvents(new BossBarEvent(), this);
		pluginManager.registerEvents(new PlayerEvents(), this);
	}
	
	@Override
	public void onDisable()
	{
		DatabaseManager.closeAllDatabaseConnections();
	}

	public HologramsBuilder getHologramBuilder()
	{
		return this.hologramsBuilder;
	}

	public BossBarBuilder getBossBarBuilder()
	{
		return this.bossBar;
	}

	public WorldManager getWorldManager()
	{
		return this.worldManager;
	}

	public static EdmineAPISpigot getInstance()
	{
		return instance;
	}

	public DatabaseUtils getDatabaseUtils()
	{
		return this.databaseUtils;
	}

	public GuiManager getGuiManager()
	{
		return this.guiManager;
	}

}
