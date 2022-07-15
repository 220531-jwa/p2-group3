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
    	String token = ctx.header("Token");
    	Dog newDog = ds.postCreateNewDog(dg, token);
    	
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
    	String token = ctx.header("Token");
        List<Dog> dogList = ds.getAllDogs(token);
    	
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
    	String token = ctx.header("Token");
    	List<Dog> dogList = ds.getAllDogsByUserId(userName, token);
    	
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
        String token = ctx.header("Token");
        Dog dg = ds.getDogById(id, token);
        
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
    	String token = ctx.header("Token");
    	
		boolean status = Boolean.parseBoolean(paramCheck);
    	List<Dog> dogList = ds.getAllDogsByStatus(userName, token, status);
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
        String token = ctx.header("Token");
        Dog dg = ds.patchUpdateDog(dgUpdate, token);
        
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
    	String token = ctx.header("Token");
        boolean dg = ds.deleteDog(id, token);
        
        if(dg) {
    		ctx.status(204);
        	ctx.json(dg);
    	} else {
    		ctx.status(404);
    		ctx.result("Dog not found");
    	}
    }
}
