package dev.group3.controller;

import java.util.ArrayList;
import java.util.List;

import dev.group3.model.Dog;
import dev.group3.repo.DogDAO;
import dev.group3.service.DogService;
import io.javalin.http.Context;

public class DogController {
	
	private static DogService ds;
    
    public DogController (DogService ds) {
    	this.ds = ds;
    }
	
    /*
     * === POST ===
     */
    
    public void createNewDog(Context ctx) {
    	Dog dg = ctx.bodyAsClass(Dog.class);
    	Dog newDog = ds.postCreateNewDog(dg);
    	
    	if(newDog != null) {
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
    
    public void getAllDogs(Context ctx) {
        List<Dog> dogList = ds.getAllDogs();
    	
    	if(dogList != null) {
    		ctx.status(200);
        	ctx.json(dogList);
    	} else {
    		ctx.status(404);
    		ctx.result("Dogs not found");
    	}
    }
    
    public void getAllDogsByUsername(Context ctx) {
    	String userName = ctx.pathParam("username");
    	List<Dog> dogList = ds.getAllDogsByUserId(userName);
    	
    	if(dogList != null) {
    		ctx.status(200);
        	ctx.json(dogList);
    	} else {
    		ctx.status(404);
    		ctx.result("Dogs not found");
    	}
    }
    
    public void getDogById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("did"));
        Dog dg = ds.getDogById(id);
        
        if(dg != null) {
    		ctx.status(200);
        	ctx.json(dg);
    	} else {
    		ctx.status(404);
    		ctx.result("Dog not found");
    	}
    }
    
    public void getAllDogsByStatus(Context ctx) {
    	String paramCheck = ctx.pathParam("status");
    	String userName = ctx.pathParam("username");
    	
		boolean status = Boolean.parseBoolean(paramCheck);
    	List<Dog> dogList = ds.getAllDogsByStatus(userName, status);
    	//Checking if parameter is null before trying to parse
    	if(dogList != null) {

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
    
    public void updateDogById(Context ctx) {
        Dog dgUpdate = ctx.bodyAsClass(Dog.class);
        Dog dg = ds.patchUpdateDog(dgUpdate);
        
        if(dg != null) {
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
    
    public void deleteDogById(Context ctx) {
    	int id = Integer.parseInt(ctx.pathParam("did"));
        boolean dg = ds.deleteDog(id);
        
        if(dg = true) {
    		ctx.status(204);
        	ctx.json(dg);
    	} else {
    		ctx.status(404);
    		ctx.result("Dog not found");
    	}
    }
}
