package dev.group3.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.DTO.MetaDTO;

public class MetaService {
    
    private static Logger log = LogManager.getLogger(MetaService.class);
    
    /**
     * Gets the meta data from the server
     * @return The meta data
     */
    public MetaDTO getMetaData() {
        log.debug("Getting Server Meta Data");
        
        return MetaDTO.getMetaDTO();
    }
}
