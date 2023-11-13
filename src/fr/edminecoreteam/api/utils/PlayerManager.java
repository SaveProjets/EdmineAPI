package fr.edminecoreteam.api.utils;

import fr.edminecoreteam.api.account.AccountInfo;

public class PlayerManager {

    /**
     * Permet de vérifier si le joueur mentionné a la permission
     * @param p Pseudo du joueur
     * @param permission Permission minimum à avoir
     * @return Boolean : le joueur a la permission ou non
     */
    public static boolean hasPermission(String p, int permission){
        AccountInfo accountInfo = new AccountInfo(p);
        if(accountInfo.getRankModule() >= permission){
            return true;
        }
        return false;
    }

    /**
     * Permet de vérifier si le joueur a un grade se trouvant entre les permissions indiquées ou non
     * @param p Pseudo du joueur
     * @param permissionMin Permission minimum
     * @param permissionMax Permission Maximum
     * @return Boolean : le joueur a une permission entre permissionMin et permissionMax ou non
     */
    public static boolean hasPermission(String p, int permissionMin, int permissionMax){
        AccountInfo accountInfo = new AccountInfo(p);
        int permissionLevel = accountInfo.getRankModule();
        if(permissionLevel <= permissionMax && permissionLevel >= permissionMin){
            return true;
        }
        return false;
    }


    /**
     * Permet de récupérer l'uuid d'un joueur à partir de son Pseudo
     * @param p Pseudo du joueur
     * @return String : UUID du joueur
     */
    public String getUUID(String p){
        AccountInfo accountInfo = new AccountInfo(p);
        return accountInfo.getUUID();
    }
}
