package dev.group3.model.DTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import dev.group3.model.enums.ResStatusType;
import dev.group3.model.enums.ServiceType;

public class MetaDTO {
    
    private static MetaDTO metaDTO;
    
    private static HashMap<String, List<ResStatusType>> resStatuses;
    private static HashMap<String, List<ServiceType>> services;
    
    private MetaDTO() {
        resStatuses = new HashMap<String, List<ResStatusType>>();
        resStatuses.put("ACTIVE", ResStatusType.getActive());
        resStatuses.put("FINISHED", ResStatusType.getFinished());
        
        services = new HashMap<String, List<ServiceType>>();
        services.put("RATE", new ArrayList<ServiceType>(Arrays.asList(ServiceType.RATE)));
        services.put("DOG SERVICES", ServiceType.getDogServices());
    }
    
    public static synchronized MetaDTO getMetaDTO() {
        if (metaDTO == null) {
            metaDTO = new MetaDTO();
        }
        return metaDTO;
    }
    
    public HashMap<String, List<ResStatusType>> getResStatuses() {
        return resStatuses;
    }
    
    public HashMap<String, List<ServiceType>> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "MetaDTO [getResStatuses()=" + getResStatuses() + ", getServices()=" + getServices() + "]";
    }
}
