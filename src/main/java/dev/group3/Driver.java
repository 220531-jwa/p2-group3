package dev.group3;

import static io.javalin.apibuilder.ApiBuilder.*;

import dev.group3.controller.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class Driver {
    
    public static void main(String[] args) {
     // Init server
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public", Location.CLASSPATH);
        });
        
        // Creating controllers
        UserController uc = new UserController();
        
        // Starting server
        app.start(8080);

        // Handling end-points
        app.routes(() -> {
            path("/login", () -> {
                post(uc::loginUserWithCredentials);
            });
            path("/logout", () -> {
                post(uc::logoutUser);
            });
            path("/users", () -> {
                post(uc::createNewUser);
            });
        }); 
        // End of end-points
    }

}
