package fr.edmine.api.managers.lists;

public enum RankList
{

	JOUEUR("§7[JOUEUR]"), 
	STAFF("§4[STAFF]");

	@SuppressWarnings("unused")
	private final String display;

	RankList(String display)
	{
		this.display = display;
	}
}
