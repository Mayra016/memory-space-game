package com.Space.Game.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.Space.Game.Entities.GuessWord;
import com.Space.Game.Entities.GuessWordDE;
import com.Space.Game.Entities.GuessWordDTO;
import com.Space.Game.Entities.GuessWordEN;
import com.Space.Game.Entities.GuessWordPT;
import com.Space.Game.Interfaces.GuessWordI;
import com.Space.Game.Models.GameDTO;
import com.Space.Game.Models.GameModel;
import com.Space.Game.Services.GameService;


@Controller
public class GameController {

    @Autowired
    GameService service;
    @Autowired
    GameDTO persistentData = new GameDTO();
    @Autowired
    private LocaleResolver localeResolver = new CookieLocaleResolver();
    String appLanguage;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("text");
    GameModel game = new GameModel();
    GameModel currentGame = new GameModel();
    List<Long> playedLevelsPersistent = new ArrayList<>();
    
    // READ
    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        
    }
    
    @GetMapping("menu") 
    public String getMenu() {
    	persistentData = new GameDTO();
    	service.setCurrentGameAlive(true);
    	if (game!=null) {
    		service.resetLevelStates();
    		game = new GameModel();
    	}
    	return "menu";
    }
    
    @GetMapping("languages") 
    public String getLanguages() {
    	return "languages";
    }
    
    @GetMapping("infos")
    public String getInfoPage() {
    	return "infos";
    }
    
    @GetMapping("/menu/{language}")
    public String changeLanguage(@PathVariable String language, HttpServletRequest request) {
    	service.setLanguage(language);
    	this.appLanguage = language;
    	Locale newLocale;
    	if ("ES".equals(language)) {
            newLocale = new Locale("es");
        } else if ("EN".equals(language)) {
            newLocale = new Locale("en");
        } else if ("PT".equals(language)) {
            newLocale = new Locale("pt", "BR"); 
        } else if ("DE".equals(language)) {
            newLocale = new Locale("de");
        } else {
            newLocale = new Locale("es"); //default
        }
        localeResolver.setLocale(request, null, newLocale);   	
    	return "redirect:/menu";
    }

    // CLEAR VARIABLES AND GET NEXT LEVEL MOVES
    @GetMapping("nextLevel")
    public String generateLevel() {
    	if (game.getLevel() < 1) {
    		game.setLevelMoves();
    	} else {
    		game.updateLevelMoves();
    	}    	
    	game.setLevel();
    	game.getPlayerMoves().clear();
    	currentGame.setAlive(true);   	
    	return "redirect:/level/".concat(String.valueOf(game.getLevel()));
    }
    
    @GetMapping("lost") 
    public String getLost() {
    	return "lost";
    }
    
    
    // CHECK IF THE PLAYER HAS LOST AND ADD PERSISTENT DATA TO THIS GAME LEVEL
    @GetMapping("/level/{gameLevel}")
    public String home(@PathVariable String gameLevel, Model model) {
    	GameModel currentGame = service.activeTimeOption();
    	//game.setScore(persistentData.getScore());
    	
    	System.out.println("Level Moves: " + game.getLevelMoves());
        if (game!=null && persistentData.getLifes()<4) {        	
        	System.out.println("Usando el persistentData");
        	game.setLifes(persistentData.getLifes());
        	//game.setScore(persistentData.getScore());
            if (currentGame.getAlive()==false) {
            	return "lost";
            }
        }
        if (currentGame.getAlive()==false && persistentData.getLifes() <= 0) {
        	return "lost";
        }
        model.addAttribute("game", game);
        model.addAttribute("currentGame", currentGame);
        model.addAttribute("persistentData", persistentData);
        return "level"; 
    }

    
    @GetMapping("/checkAnswer/{userInput}")
    public String checkAnswer(@PathVariable ArrayList<String> userInput, Model model) {
    	GameModel currentGame = service.activeTimeOption();

        if (game!=null) {
        	
        	System.out.println("Lifes (antes): " + game.getLifes());
        	byte lifes = game.getLifes();
        	//game.setScore(persistentData.getScore());
        	model.addAttribute("game", game);
        	AtomicBoolean win = service.compareAnswer(game.getLevelMoves(), userInput); 
        	System.out.println("win value " + win.get());
        	if ( win.get() &&  lifes > 0 ) {
        		System.out.println(win + "lifes>0");
        		System.out.println("Score" + game.getScore());
        		game.setScore(currentGame.getScoreMultiplier());
        		model.addAttribute("game", game);
        		model.addAttribute("persistentData", persistentData);
        		return "redirect:/nextLevel";
        		
        	} else if ( win.get() == false && lifes > 0) {
        		System.out.println("repuesta" + win + "lifes>0");        	
        		game = service.reduceLife(lifes, game);
        		persistentData.setLifes(game.getLifes());
        		System.out.println("Lifes persistentData: " + persistentData.getLifes());
        		model.addAttribute("game", game);
        		model.addAttribute("persistentData", persistentData);
        		return "level";
        	}
        	if ( win.get() == false && lifes <= 0) {
        		game.getLevelMoves().clear();
        		game.getPlayerMoves().clear();
	        	game = null;
	        	model.addAttribute("persistentData", persistentData);
	        	return "lost";
	        	
        	}
        	System.out.println("Lifes: " + game.getLifes());
        	
        	model.addAttribute("game", game);
        }
        
		return "level";	
    }
   
}
