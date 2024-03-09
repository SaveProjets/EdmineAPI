package fr.edmine.api.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseAccess
{

	private final DatabaseCredentials credentials;
	private HikariDataSource hikariDataSource;

	public DatabaseAccess(DatabaseCredentials credentials)
	{
		this.credentials = credentials;
	}

	private void setupHikariCP()
	{
		final HikariConfig hikariConfig = new HikariConfig();

		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setJdbcUrl(credentials.toURL());
		hikariConfig.setUsername(credentials.getUsername());
		hikariConfig.setPassword(credentials.getPassword());
		hikariConfig.setMaxLifetime(600000L);
		hikariConfig.setIdleTimeout(300000L);
		hikariConfig.setLeakDetectionThreshold(300000L);
		hikariConfig.setConnectionTimeout(10000L);
		hikariConfig.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());

		this.hikariDataSource = new HikariDataSource(hikariConfig);
	}

	public void initPool()
	{
		setupHikariCP();
	}

	public void closePool()
	{
		if (this.hikariDataSource == null)
		{
			setupHikariCP();
		}
		this.hikariDataSource.close();
	}

	public Connection getConnection() throws SQLException
	{
		if (this.hikariDataSource == null)
		{
			System.out.println("Not connected to DataBase");
			setupHikariCP();
		}
		return this.hikariDataSource.getConnection();
	}

}
