package fr.edmine.api.database;

public class DatabaseCredentials
{

	private final String hostname;
	private final String username;
	private final String password;
	private final String dataBaseName;
	private final int port;

	public DatabaseCredentials(String hostname, String username, String password, String dataBaseName, int port)
	{
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.dataBaseName = dataBaseName;
		this.port = port;
	}

	public String toURL()
	{

		return "jdbc:mysql://" + hostname + ":" + port + "/" + dataBaseName;
	}

	public String getHostname()
	{
		return this.hostname;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String getDataBaseName()
	{
		return this.dataBaseName;
	}
}
