package dev.group3.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ResStatusType {
    REGISTERED,
    CHECKEDIN,
    CHECKEDOUT,
    CANCELLED;
    
    public static List<ResStatusType> getActive() {
        return new ArrayList<ResStatusType>(Arrays.asList(REGISTERED, CHECKEDIN));
    }
    
    public static List<ResStatusType> getFinished() {
        return new ArrayList<ResStatusType>(Arrays.asList(CHECKEDOUT, CANCELLED));
    }
}
