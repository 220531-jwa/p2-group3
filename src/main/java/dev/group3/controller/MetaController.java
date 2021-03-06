package dev.group3.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.DTO.MetaDTO;
import dev.group3.service.MetaService;
import io.javalin.http.Context;

public class MetaController {
    
    private MetaService metaService = new MetaService();
    private static Logger log = LogManager.getLogger(MetaController.class);
    
    /**
     * Handles the http get request for getting meta information from the server
     * @param 200 with meta info, or 500 server error otherwise (like the server is off or something)
     */
    public void getMetaData(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /meta");
        
        // Attempting to get meta information
        MetaDTO metaDTO = metaService.getMetaData();
        
        // Sending back meta information
        ctx.json(metaDTO);
        ctx.status(200);
    }
}
