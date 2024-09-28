package fr.edminecoreteam.api.management.list;

import java.util.HashMap;

public enum RankList {

    JOUEUR("§7Joueur"),
    VIP("§F§lVIP"),
    SUPERVIP("§e§lSUPER-VIP"),
    SUPREME("§a§lSUPREME"),
    ULTRA("§bULTRA"),
    ELITE("§3ELITE");

    private String display;

    public String getDisplay() {
        return display;
    }

    RankList(String display) {
        this.display = display;
    }
}
