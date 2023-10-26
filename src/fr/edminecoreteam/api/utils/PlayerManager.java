package fr.edminecoreteam.api.utils;

import fr.edminecoreteam.api.account.AccountInfo;

public class PlayerManager {
    public static boolean hasPermission(String p, int permission){
        AccountInfo accountInfo = new AccountInfo(p);
        if(accountInfo.getRankModule() >= permission){
            return true;
        }
        return false;
    }
}
