package com.Guess.Word.Entities;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.Guess.Word.Interfaces.GuessWordI;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;


@Entity
@Table(name="gamePT")
public class GuessWordPT implements GuessWordI{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("level")
    private Long level;
    @Column
    @JsonProperty("clue")
    private String clue;
    @Column
    @JsonProperty("word")
    private String word;
    
    @Transient
    private byte lifes;
    @Transient
    private boolean alive;
    @Transient
    private List<Long> playedLevels = new ArrayList<>();
    @Transient
    private int score;
    @Transient
    private String userInput;
    @Transient
    private long startTime;
    @Transient
    private ArrayList<Character> letters = new ArrayList<>();
    @Transient
    private byte scoreMultiplier;
    
    public GuessWordPT() {
    	
    }
    
    public GuessWordPT(Long level, String clue, String word) {       
    	this.startTime = System.currentTimeMillis();
    	this.level = level;
        this.clue = clue;
        this.word = word;
        lifes = (byte) 3;
        this.alive = true;
        this.playedLevels.add(level);
        setLetters(word);
        
    }
    
    public void setScoreMultiplier( byte newScoreMultiplier) {
    	this.scoreMultiplier = newScoreMultiplier;
    }
    
    public byte getScoreMultiplier() {
    	return this.scoreMultiplier;
    }
    
    public void setLetters(String word) {
    	IntStream.range(0,word.length())
    		.forEach(i -> letters.add(word.charAt(i)));
    }
    
    public ArrayList<Character> getLetters() {
    	return this.letters;
    }
    
    public char getLettersAtIndex(byte index) {
    	return this.letters.get(index);
    }
    
    public void setStartTime() {
    	this.startTime = System.currentTimeMillis();;
    }
    
    public long getStartTime() {
    	return this.startTime;
    }
    
    public void setUserInput(String newUserInput) {
    	this.userInput = newUserInput;
    }
    
    public String getUserInput() {
    	return this.userInput;
    }


	public void setLevel(Long newLevel) {
        this.level = newLevel;
    }
    
    public Long getLevel() {
        return this.level;
    }
    
    public void setClue(String newClue) {
        this.clue = newClue;
    }
    
    public String getClue() {
        return this.clue;
    }
    
    public void setWord(String newWord) {
        this.word = newWord;
    }

	public String getWord() {
		return this.word;
	}
    
    public void setLifes(int i) {
        this.lifes = (byte) i;
    }

    public byte getLifes() {
        return lifes;
    }
    
    public void setAlive(boolean newState) {
        this.alive = newState;
    }
    
    public boolean getAlive() {
        return this.alive;
    }

    public void setPlayedLevels(List<Long> levels) {
    	if (levels!=null) {
    		this.playedLevels = levels;
    	}	
    }
    
    public List<Long> getPlayedLevels() {
        return this.playedLevels;
    }

	
	public void setScore(int newScore) {
		this.score = newScore;
	}
	
	public int getScore() {
		return this.score;
	}

	@Override
	public void setLetters(ArrayList<Character> arrayList) {
		this.letters = arrayList;	
	}
}
