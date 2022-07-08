package dev.group3;

import static io.javalin.apibuilder.ApiBuilder.path;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Driver {
    
    public static void main(String[] args) {
     // Init server
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public", Location.CLASSPATH);
        });
        
        // Creating controllers
        
        // Starting server
        app.start(8080);

        // Handling end-points
        app.routes(() -> {
            path("/login", () -> {
                
            });
            // and so on
        }); 
        
        // End of end-points
    }

}
