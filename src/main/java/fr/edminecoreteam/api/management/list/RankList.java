package fr.edminecoreteam.api.management.list;

public enum RankList {

    JOUEUR("§7[JOUEUR]"),
    STAFF("§4[STAFF]");

    private String display;

    RankList(String display) {
        this.display = display;
    }
}
