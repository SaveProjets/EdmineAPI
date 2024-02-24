package fr.edminecoreteam.api.management.list;

public enum StaffRankList {

    ADMIN("§4[ADMIN]"),
    DEV("§b[DEV]"),
    MODO("§2[MODO]"),
    NONE("§7");

    private final String display;

    StaffRankList(String display) {
        this.display = display;
    }

}
