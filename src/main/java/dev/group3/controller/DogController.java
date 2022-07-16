package dev.group3.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.group3.model.Dog;
import dev.group3.service.DogService;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;

public class DogController {
	
	private DogService ds;
	private static Logger log = LogManager.getLogger(DogController.class);
	
	// Use default dog service
	public DogController() {
	    ds = new DogService();
	}
    
	// Use specified dog service
    public DogController (DogService ds) {
    	this.ds = ds;
    }
	
    /*
     * === POST ===
     */
    
    /**
     * Handles a post request for creating a new dog
     * Takes the username from the path
     * Takes the token from the header
     * Takes the dogData from the body
     * @return 201 with dog information, and 400 otherwise
     */
    public void createNewDog(Context ctx) {
        log.debug("Recieved HTTP POST request at endpoint /dogs/{username}");
        
        // Getting user input
    	String token = ctx.header("Token");
    	Dog dogData = ctx.bodyAsClass(Dog.class);
    	
    	// Attempting to create a new dog
    	Dog newDog = ds.postCreateNewDog(dogData, token);
    	
    	// Checking if the dog was successfully created
    	if(newDog != null) {
    	    log.info("Dog successfully created");
    		ctx.status(201);
        	ctx.json(newDog);
    	} else {
    		ctx.status(400);
    		ctx.result("Dog creation failed");
    	}
    }
    
    /*
     * === GET ===
     */
    
    /**
     * Handles a get request for getting all dogs
     * Takes the token from the header
     * @return 200 with list of dogs if successful, and 404 otherwise
     */
    public void getAllDogs(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /dogs");
        
        // Getting user input
    	String token = ctx.header("Token");
    	
    	// Attempting to get all dogs
        List<Dog> dogList = ds.getAllDogs(token);
    	
        // Checking if dog list was successfully retrieved
    	if(dogList != null) {
    	    log.info("Successfully got all dogs");
    		ctx.status(200);
        	ctx.json(dogList);
    	} else {
    		ctx.status(404);
    		ctx.result("Dogs not found");
    	}
    }
    
    /**
     * Handles a get request for getting all dogs associated with a given username
     * Takes the username from the path
     * Takes the token from the header
     * @return 200 with list of dogs if successful, and 404 otherwise
     */
    public void getAllDogsByUsername(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /dogs/{username}");
        
        // Getting user input
    	String userName = ctx.pathParam("username");
    	String token = ctx.header("Token");
    	
    	// Attempting to get all dogs associated with user
    	List<Dog> dogList = ds.getAllDogsByUserId(userName, token);
    	
    	// Checking if dogs were successfully retrieved
    	if(dogList != null) {
    	    log.info("Successfully got all dogs associated with user");
    		ctx.status(200);
        	ctx.json(dogList);
    	} else {
    		ctx.status(404);
    		ctx.result("Dogs not found");
    	}
    }
    
    /**
     * Handles a get request for getting a specific dog
     * Takes the username from the path
     * Takes the dog id from the path
     * Takes the token from the header
     * @return 200 with dog information if successful, and 404 otherwise
     */
    public void getDogById(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /dogs/{username}/{did}");
        
        // Getting user input
        Validator<Integer> vid = ctx.pathParamAsClass("did", Integer.class);
        Integer id = vid.getOrDefault(null);
        String token = ctx.header("Token");
        
        // Attempting to get dog
        Dog dg = ds.getDogById(id, token);
        
        // Checking if dog was successfully found
        if(dg != null) {
            log.info("Successfully got dog");
    		ctx.status(200);
        	ctx.json(dg);
    	} else {
    		ctx.status(404);
    		ctx.result("Dog not found");
    	}
    }
    
    /**
     * Handles a get request for getting all dogs with a specified status
     * Takes the username from the path
     * Takes the dog id from the path
     * Takes the status from the path
     * Takes the token from the header
     * @return 200 with dog list if successful, and 404 otherwise
     */
    public void getAllDogsByStatus(Context ctx) {
        log.debug("Recieved HTTP GET request at endpoint /dogs/{username}/{did}/{status}");
        
        // Getting user input
        Validator<Boolean> vstatus = ctx.pathParamAsClass("status", Boolean.class);
        Boolean status = vstatus.getOrDefault(null);
    	String userName = ctx.pathParam("username");
    	String token = ctx.header("Token");
    	
    	// Attempting to get dogs of the given status
    	List<Dog> dogList = ds.getAllDogsByStatus(userName, token, status);
    	
    	// Checking if dogs were successfully retrieved
    	if(dogList != null) {
    	    log.info("Successfully got dogs");
    		ctx.status(200);
        	ctx.json(dogList);
    	} else {
    		ctx.status(404);
    		ctx.result("Dogs not found");
    	}
    }
    
    /*
     * === PATCH ===
     */
    
    /**
     * Handles a patch request for updating a specific dog
     * Takes the username from the path
     * Takes the dog id from the path
     * Takes the token from the header
     * @return 200 with updated dog information, 404 otherwise
     */
    public void updateDogById(Context ctx) {
        log.debug("Recieved HTTP PATCH request at endpoint /dogs/{username}/{did}");
        
        // Getting user input
        Dog dgUpdate = ctx.bodyAsClass(Dog.class);
        String token = ctx.header("Token");
        
        // Attempting to update dog
        Dog dg = ds.patchUpdateDog(dgUpdate, token);
        
        // Checking if dog was successfully updated
        if(dg != null) {
            log.info("Successfully updated dog");
        	ctx.status(200);
        	ctx.json(dg);
        } else {
    		ctx.status(404);
    		ctx.result("Dog failed to update");
    	}
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Handles a delete request for deleting a specific dog
     * Takes the username from the path
     * Takes the dog id from the path
     * Takes the token from the header
     * @return 200 with true, and 404 otherwise
     */
    public void deleteDogById(Context ctx) {
        log.debug("Recieved HTTP DELETE request at endpoint /dogs/{username}/{did}");
        
        // Getting user input
        Validator<Integer> vid = ctx.pathParamAsClass("did", Integer.class);
        Integer id = vid.getOrDefault(null);
    	String token = ctx.header("Token");
    	
    	// Attempting to delete dog
        boolean dg = ds.deleteDog(id, token);
        
        // Checking if dog was successfully deleted
        if(dg) {
            log.info("Succssfully deleted dog");
    		ctx.status(204);
        	ctx.json(dg);
    	} else {
    		ctx.status(404);
    		ctx.result("Dog not found");
    	}
    }
}
