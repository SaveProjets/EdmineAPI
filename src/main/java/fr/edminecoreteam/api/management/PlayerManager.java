package fr.edminecoreteam.api.management;

import fr.edminecoreteam.api.management.list.RankList;
import fr.edminecoreteam.api.management.list.StaffRankList;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager {

    private RankList rank;
    private StaffRankList staffRank;
    private int soul_fragment, divine_radiance, money, level;
    private String guild;

    private static HashMap<Player, PlayerManager> players = new HashMap<>();

    public PlayerManager(Player p, RankList rank, StaffRankList staffRank, int soul_fragment, int divine_radiance, int money, int level, String guild){
        this.rank = rank;
        this.staffRank = staffRank;
        this.soul_fragment = soul_fragment;
        this.divine_radiance = divine_radiance;
        this.money = money;
        this.level = level;
        this.guild = guild;
        players.put(p, this);
    }

    /**
     * Permet de récupérer le PlayerManager du joueur
     * @param p Player
     * @return PlayerManager
     */
    public static PlayerManager getPlayer(Player p){
        return players.get(p);
    }

    /**
     * Permet de verifier si un joueur existe
     * @param p Player
     */
    public static boolean exist(Player p){
        return players.containsKey(p);
    }

    /**
     * Permet de retirer un joueur
     * @param p Player
     */
    public static void removePlayer(Player p){
        players.remove(p);
    }

    /**
     * Permet de récupérer le rank du joueur
     * @return RankList
     */
    public RankList getRank() {
        return rank;
    }

    /**
     * Permet de récupérer le staff rank du joueur
     * @return StaffRankList
     */
    public StaffRankList getStaffRank() {
        return staffRank;
    }

    /**
     * Permet de récupérer le nombre de soul fragment du joueur
     * @return int
     */
    public int getSoulFragment() {
        return soul_fragment;
    }

    /**
     * Permet de récupérer le nombre de divine radiance du joueur
     * @return int
     */
    public int getDivineRadiance() {
        return divine_radiance;
    }

    /**
     * Permet de récupérer l'argent du joueur
     * @return int
     */
    public int getMoney() {
        return money;
    }

    /**
     * Permet de récupérer le niveau du joueur
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Permet de récupérer la guilde du joueur
     * @return String
     */
    public String getGuild() {
        return guild;
    }

    /**
     * Permet de changer le rank du joueur
     * @param rank RankList
     */
    public void setRank(RankList rank) {
        this.rank = rank;
    }

    /**
     * Permet de changer le staff rank du joueur
     * @param staffRank StaffRankList
     */
    public void setStaffRank(StaffRankList staffRank) {
        this.staffRank = staffRank;
    }

    /**
     * Permet de changer le nombre de soul fragment du joueur
     * @param soul_fragment int
     */
    public void setSoulFragment(int soul_fragment) {
        this.soul_fragment = soul_fragment;
    }

    /**
     * Permet de changer le nombre de divine radiance du joueur
     * @param divine_radiance int
     */
    public void setDivineRadiance(int divine_radiance) {
        this.divine_radiance = divine_radiance;
    }

    /**
     * Permet de changer l'argent du joueur
     * @param money int
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Permet de changer le niveau du joueur
     * @param level int
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Permet de changer la guilde du joueur
     * @param guild String
     */
    public void setGuild(String guild) {
        this.guild = guild;
    }

    /**
     * Permet d'ajoouter des soul fragment au joueur
     * @param soul_fragment int
     */
    public void addSoulFragment(int soul_fragment) {
        this.soul_fragment += soul_fragment;
    }

    /**
     * Permet d'ajoouter des divine radiance au joueur
     * @param divine_radiance int
     */
    public void addDivineRadiance(int divine_radiance) {
        this.divine_radiance += divine_radiance;
    }

    /**
     * Permet d'ajoouter de l'argent au joueur
     * @param money int
     */
    public void addMoney(int money) {
        this.money += money;
    }

    /**
     * Permet d'ajoouter des niveaux au joueur
     * @param level int
     */
    public void addLevel(int level) {
        this.level += level;
    }

    /**
     * Permet de retirer des soul fragment au joueur
     * @param soul_fragment int
     */
    public void removeSoulFragment(int soul_fragment) {
        this.soul_fragment -= soul_fragment;
    }

    /**
     * Permet de retirer des divine radiance au joueur
     * @param divine_radiance int
     */
    public void removeDivineRadiance(int divine_radiance) {
        this.divine_radiance -= divine_radiance;
    }

    /**
     * Permet de retirer de l'argent au joueur
     * @param money int
     */
    public void removeMoney(int money) {
        this.money -= money;
    }

    /**
     * Permet de retirer des niveaux au joueur
     * @param level int
     */
    public void removeLevel(int level) {
        this.level -= level;
    }
}
