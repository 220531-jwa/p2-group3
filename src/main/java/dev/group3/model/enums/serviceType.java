package dev.group3.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum serviceType {
    RATE,
    GROOMING,
    BELLYRUB,
    DOGWALK,
    TRIMNAILS;
    
    public static List<serviceType> getDogServices() {
        return new ArrayList<serviceType>(Arrays.asList(GROOMING, BELLYRUB, DOGWALK, TRIMNAILS));
    }
}
