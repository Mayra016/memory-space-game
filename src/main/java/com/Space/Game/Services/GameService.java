package com.Space.Game.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.Space.Game.Entities.GuessWord;
import com.Space.Game.Entities.GuessWordDE;
import com.Space.Game.Entities.GuessWordEN;
import com.Space.Game.Entities.GuessWordPT;
import com.Space.Game.Interfaces.GuessWordI;
import com.Space.Game.Models.GameModel;
import com.Space.Game.Repositories.GuessWordDERepository;
import com.Space.Game.Repositories.GuessWordENRepository;
import com.Space.Game.Repositories.GuessWordPTRepository;
import com.Space.Game.Repositories.GuessWordRepository;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class GameService {
    
    Long currentLevelService;
    GameModel currentGame = new GameModel();
    String appLanguage = "ES";
    boolean sameLevel = false;
    
    // Set app language to evaluate from which data basis should be the level infos get from
    public void setLanguage(String language) {
    	this.appLanguage = language;
    }
    
    // Set alive when start to play
    public void setCurrentGameAlive(boolean newState) {
    	this.currentGame.setAlive(newState);
    }
      
    
    // Reset previous game letters and interrupt scheduled execution
    public void resetLevelStates() {
    	this.sameLevel=false;
    	this.currentGame.setAlive(false);
    }
    
    // READ LEVEL
    public GameModel getLevel(Long level) {
        this.currentLevelService = level;
        GameModel newGame = new GameModel();

        if (this.sameLevel==false) {
        	currentGame.setStartTime();
        	currentGame.setAlive(true);
        	this.sameLevel = true;
        }	
        return newGame;
    }

    
    // COMPARE ANSWER
    public AtomicBoolean compareAnswer(List<String> levelMoves, List<String> userMoves) {
    	AtomicBoolean check = new AtomicBoolean(true);
    	System.out.println(userMoves);
    	
    	try {   		
    		if (currentGame!=null) {
    			for ( byte i = 0; i < levelMoves.size(); i++) {
    				if (!levelMoves.get(i).equals(userMoves.get(i))) {
    					check.set(false);
    					System.out.println(userMoves.get(i) + "userMoves" + levelMoves.get(i));
    				}
    			}
    	        return check;
            } else {
            	check.set(false);
            	this.sameLevel = true;
            	return check;
            }
    	} catch(Exception e) {
    		this.sameLevel = true;
    		System.out.println(e.toString());
    		check.set(false);
    		return check;
    	}
    }
    
    // CHECK TIME 
    @Scheduled(fixedDelay = 1000)
    public GameModel activeTimeOption() {
    	if (currentLevelService != null) {
	        if (currentGame!=null && currentGame.getAlive()==true) {
	        	long currentTime = System.currentTimeMillis() - currentGame.getStartTime();
	        	System.out.println(TimeUnit.MILLISECONDS.toSeconds(currentTime));
	        	// In 9 seconds will the first clue letter be displayed on screen
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 9) {       			                
	                currentGame.setScoreMultiplier((byte)30);
	        	}
	        	
	        	// Update score multiplier value
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 22) { 
	        		currentGame.setScoreMultiplier((byte)20);
	        	}
	        	
	        	// Update score multiplier value
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 40) { 
	        		currentGame.setScoreMultiplier((byte)10);
	        	}
	        	
	        	// End the scheduled function to spare resources
	        	if (TimeUnit.MILLISECONDS.toMinutes(currentTime) >= 3) {       		
	        		currentGame.setAlive(false);
	        	}
	        }	
        }
    	return currentGame;
        
    }
    
    // REDUCE LIFE
    public GameModel reduceLife(byte lifes, GameModel game) {
        if (lifes > -1) {
        	game.setLifes((byte) (lifes-1));
        	currentGame.setLifes((byte) (lifes - 1));
        } else {
        	game.setAlive(false);
        }
        return game;
    }
    
    // DETERMINE IF THE PLAYER HAS LOST
    public boolean hasLost(GameModel game) {
        return game.getAlive();
    }
    
  
}
