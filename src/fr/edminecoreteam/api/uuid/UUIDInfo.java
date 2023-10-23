package fr.edminecoreteam.api.uuid;

import java.util.HashMap;
import java.util.Map;

public class UUIDInfo {

    private Map<String, UUIDInfo> uuidInfo;
    private String p;
    private UUIDData uuidData;

    public UUIDInfo(String p){
        this.p = p;
        this.uuidInfo = new HashMap<String, UUIDInfo>();
        this.uuidData = new UUIDData(p);
        this.uuidInfo.put(p, this);
    }

    public String getUUID(){ return uuidData.getUUID();}

}
