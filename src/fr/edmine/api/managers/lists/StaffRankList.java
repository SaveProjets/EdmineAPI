package fr.edmine.api.managers.lists;

public enum StaffRankList
{

	ADMIN("§4[ADMIN]"), 
	DEV("§b[DEV]"), 
	MODO("§2[MODO]"), 
	NONE("§7");

	@SuppressWarnings("unused")
	private final String display;

	StaffRankList(String display)
	{
		this.display = display;
	}

}
