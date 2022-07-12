package dev.group3;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import dev.group3.controller.DogController;
import dev.group3.controller.ReservationController;
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
        DogController dc = new DogController();
        ReservationController rc = new ReservationController();

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
                path("/{username}", () -> {
                    get(uc::getUserByUsername);
                    patch(uc::updateUserByUsername);
                });
            });
            path("/dogs", () -> {
                get(dc::getAllDogs);
                path("/{username}", () -> {
                    post(dc::createNewDog);
                    get(dc::getAllDogsByUsername);
                });
                path("/{did}", () -> {
                    get(dc::getDogById);
                    patch(dc::updateDogById);
                    delete(dc::deleteDogById);
                });
            });
            path("/reservations", () -> {
                get(rc::getAllReservations);
                path("/{username}", () -> {
                    post(rc::createReservation);
                    get(rc::getAllRservationsByUsername);
                });
                path("/{rid}", () -> {
                    get(rc::getReservationById);
                    patch(rc::updateReservationById);
                });
            });
        });
        // End of end-points
    }
}
