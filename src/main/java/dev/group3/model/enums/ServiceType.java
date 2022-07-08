package dev.group3.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ServiceType {
    RATE,
    GROOMING,
    BELLYRUB,
    DOGWALK,
    TRIMNAILS;
    
    public static List<ServiceType> getDogServices() {
        return new ArrayList<ServiceType>(Arrays.asList(GROOMING, BELLYRUB, DOGWALK, TRIMNAILS));
    }
}
