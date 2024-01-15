package com.Guess.Word.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.Guess.Word.Entities.GuessWord;
import com.Guess.Word.Entities.GuessWordDE;
import com.Guess.Word.Entities.GuessWordEN;
import com.Guess.Word.Entities.GuessWordPT;
import com.Guess.Word.Interfaces.GuessWordI;
import com.Guess.Word.Repositories.GuessWordDERepository;
import com.Guess.Word.Repositories.GuessWordENRepository;
import com.Guess.Word.Repositories.GuessWordPTRepository;
import com.Guess.Word.Repositories.GuessWordRepository;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class GuessWordService {
	// REPOSITORIES
    @Autowired
    GuessWordRepository repository;
    @Autowired
    GuessWordDERepository repositoryDE;
    @Autowired
    GuessWordPTRepository repositoryPT;
    @Autowired
    GuessWordENRepository repositoryEN;    
    
    Long currentLevelService;
    Long levelsNum = Long.valueOf(50); // Number of levels on this language data basis
    GuessWord currentGame = new GuessWord();
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
    
    // CRUD
    
    // CREATE LEVEL   
    public void addNewLevel(Long level, String clue, String word) {
    	GuessWord newLevel = new GuessWord(level, clue, word);
        repository.save(newLevel);
    }
    
    // GENERATE FIRST LEVEL
    public Long generateLevel() {
    	if ("ES".equals(appLanguage)) {
    		levelsNum = repository.count() - 1;
    	} else if ("PT".equals(appLanguage)) {
    		levelsNum = repositoryPT.count() - 1;
    	} else if ("DE".equals(appLanguage)) {
    		levelsNum = repositoryDE.count() - 1;
    	} else if ("EN".equals(appLanguage)) {
    		levelsNum = repositoryEN.count() - 1;
    	} else {
    		levelsNum = repository.count() - 1;
    	}

    	
    	Random randomize = new Random();
    	Long nextLevel = randomize.nextLong(1, levelsNum);
    	this.currentGame.setLifes((byte) 3);
    	this.currentGame.setPlayedLevels(null);
    	this.currentGame.setAlive(true);
    	this.currentGame.setScoreMultiplier((byte)30);
    	return nextLevel;
    }
    
    // Reset previous game letters and interrupt scheduled execution
    public void resetLetters() {
    	this.currentGame.setLetters("");
    	this.sameLevel=false;
    	this.currentGame.setAlive(false);
    }
    
    // READ LEVEL
    public GuessWord getLevel(Long level) {
        this.currentLevelService = level;
        GuessWord newGame;
        GuessWordI game2;
    	if (appLanguage.equals("ES")) {
    		Optional<GuessWord> gameES = repository.findById(level);
    		game2 = gameES.get();
    	} else if (appLanguage.equals("PT")) {
    		Optional<GuessWordPT> gamePT = repositoryPT.findById(level);
    		game2 = gamePT.get();
    	} else if (appLanguage.equals("DE")) {
    		Optional<GuessWordDE> gameDE = repositoryDE.findById(level);
    		game2 = gameDE.get();
    	} else if (appLanguage.equals("EN")) {
    		Optional<GuessWordEN> gameEN = repositoryEN.findById(level);
    		game2 = gameEN.get();
    	} else {
    		Optional<GuessWord> gameES = repository.findById(level);
    		game2 = gameES.get();
    	}
    	if (game2!=null) {
	    	String clue = game2.getClue();
	        System.out.println("getLevel clue1" + clue);
	        newGame = new GuessWord(level, clue, game2.getWord());
	        currentGame.setWord(newGame.getWord());   

    	} else     {
    		Long nextLevel = chooseNextLevel(game2);
    		getLevel(nextLevel);
    		GuessWord gameI = getLevel(nextLevel);
	    	String clue = gameI.getClue();
	        System.out.println("getLevel clue" + clue);
	        newGame = new GuessWord(level, clue, gameI.getWord());
    		return newGame;
    		
    	}
        if (this.sameLevel==false) {
        	currentGame.setLetters("");
        	currentGame.setStartTime();
        	currentGame.setAlive(true);
        	this.sameLevel = true;
        }	
        return newGame;
    }
    

    // GET ALL LEVELS
    public List<GuessWord> findAll() {
        return this.repository.findAll();
    }
    
    // UPDATE LEVEL
    public void updateLevel(Long level, String clue, String word) {
        Optional<GuessWord> game = repository.findById(level);
        if (game.isPresent()) {
        	GuessWord updateGame = game.get();
        	updateGame.setClue(clue);
        	updateGame.setWord(word);
        	updateGame.setLevel(level);
        }    
    }
    
 // UPDATE DATA BASE
    public void updateDataBase(int initialLevel, int finalLevel, String language) throws Exception {
        try {
            // Load the xlsx that contains the clue and word information for the levels and update
        	// the data basis
            Resource resource = new ClassPathResource("static/Trivia 31.12.xlsx");
            File file = resource.getFile();
            try (InputStream inp = new FileInputStream(file)) {
                Workbook workbook = new XSSFWorkbook(inp);
                Sheet sheet = workbook.getSheetAt(0);
                
                for (int level = initialLevel; level <= finalLevel; level++) {
                    String word = "a", clue ="a";

                    if (language.equals("ES")) {
                        word = sheet.getRow((int) level).getCell(0).getStringCellValue();
                        clue = sheet.getRow((int) level).getCell(1).getStringCellValue();
                    } else if (language.equals("EN")) {
                        word = sheet.getRow((int) level).getCell(22).getStringCellValue();
                        clue = sheet.getRow((int) level).getCell(23).getStringCellValue();
                    } else if (language.equals("DE")) {
                        word = sheet.getRow((int) level).getCell(18).getStringCellValue();
                        clue = sheet.getRow((int) level).getCell(19).getStringCellValue();
                    } else if (language.equals("PT")) {
                        word = sheet.getRow((int) level).getCell(3).getStringCellValue();
                        clue = sheet.getRow((int) level).getCell(4).getStringCellValue();
                    }

                    System.out.println(word);
                    System.out.println("Dentro del try");
                    
                    if ("ES".equals(language)) {
                        GuessWord newLevel = new GuessWord((long) level, clue, word);
                        repository.save(newLevel);
                    } else if ("EN".equals(language)) {
                        GuessWordEN newLevelEN = new GuessWordEN((long) level, clue, word);
                        repositoryEN.save(newLevelEN);
                    } else if ("PT".equals(language)) {
                        GuessWordPT newLevelPT = new GuessWordPT((long) level, clue, word);
                        repositoryPT.save(newLevelPT);
                    } else if ("DE".equals(language)) {
                        GuessWordDE newLevelDE = new GuessWordDE((long) level, clue, word);
                        repositoryDE.save(newLevelDE);
                    }
                }    
            }           
        } catch (IOException e) {
            System.out.println("exception");
            System.out.println(e);     
            throw e;
        } catch (Exception p) {
            System.out.println(p);
            throw p;
        }  
    }

    public String letterToString(char letter) {
    	StringBuilder inputLetters = new StringBuilder();
    	inputLetters.append(letter);
    	return inputLetters.toString();
    }
    
    // DELETE LEVEL
    public void deleteLevel(Long level) {
        Optional<GuessWord> levelToDelete = repository.findById(level);
        if (levelToDelete.isPresent()) {
        	GuessWord toDelete = levelToDelete.get();
        	repository.delete(toDelete);
        }
    }
    
    // COMPARE ANSWER
    public boolean compareAnswer(Long level, String userInput) {
    	try {
    		if (currentGame!=null) {
    	        String levelAnswer = currentGame.getWord();
    	        if( levelAnswer.equalsIgnoreCase(userInput) ) {
    	        	this.sameLevel = false;
    	            return true;
    	        } else if (userInput.trim().isEmpty() || userInput == null) {
    	        	this.sameLevel = true;
    	        	return false;
    	        } else {
    	        	this.sameLevel = true;
    	            return false;
    	        }
            } else if (userInput==null) {
            	this.sameLevel = true;
            	return false;
            } else {
            	this.sameLevel = true;
            	return false;
            }
    	} catch(Exception e) {
    		this.sameLevel = true;
    		System.out.println(e.toString());
    		return false;
    	}
    }
    
    // CHECK TIME 
    @Scheduled(fixedDelay = 1000)
    public GuessWordI activeTimeOption() {
    	if (currentLevelService != null) {
	        if (currentGame!=null && currentGame.getAlive()==true) {
	        	String word = currentGame.getWord();
	    		ArrayList<Character> letters = currentGame.getLetters();
	        	long currentTime = System.currentTimeMillis() - currentGame.getStartTime();
	        	System.out.println(TimeUnit.MILLISECONDS.toSeconds(currentTime));
	        	// In 9 seconds will the first clue letter be displayed on screen
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 9) {       			                
	                currentGame.setScoreMultiplier((byte)30);
	        		System.out.println("9 s" + currentGame.getLetters());
	        	}
	        	
	        	// Update score multiplier value
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 22) { 
	        		currentGame.setScoreMultiplier((byte)20);
	        		System.out.println("22s" +letters);
	        	}
	        	
	        	// Update score multiplier value
	        	if (TimeUnit.MILLISECONDS.toSeconds(currentTime) == 40) { 
	        		currentGame.setScoreMultiplier((byte)10);
	        	}
	        	
	        	// End the scheduled function to spare resources
	        	if (TimeUnit.MILLISECONDS.toMinutes(currentTime) >= 3) {       		
	        		currentGame.setAlive(false);
	        	}
	        	System.out.println(currentGame.getLetters());
	        }	
        }
    	return currentGame;
        
    }
    
    // REDUCE LIFE
    public GuessWordI reduceLife(byte lifes, GuessWordI trivia) {
        if (lifes > -1) {
        	trivia.setLifes((byte) (lifes-1));
        	currentGame.setLifes((byte) (lifes - 1));
        } else {
        	trivia.setAlive(false);
        }
        return trivia;
    }
    
    // DETERMINE IF THE PLAYER HAS LOST
    public boolean hasLost(GuessWordI game) {
        return game.getAlive();
    }
    
    // DETERMINE THE NEXT LEVEL
    public Long chooseNextLevel(GuessWordI trivia) {
        Random randomize = new Random();
        Long nextLevel = randomize.nextLong(1, levelsNum);
        Long currentLevel = trivia.getLevel();
        List<Long> playedLevels = trivia.getPlayedLevels();
        while(playedLevels.contains(nextLevel) || nextLevel == currentLevel) {
            nextLevel = randomize.nextLong(1, 100);            
        }
        return nextLevel;
    }
  
}
