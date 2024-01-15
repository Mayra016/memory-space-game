package com.Guess.Word.Entities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.Guess.Word.Interfaces.GuessWordI;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;


@Entity
@Table(name="game")
public class GuessWord implements GuessWordI{
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
    
    public GuessWord() {
    	
    }
    
    public GuessWord(Long level, String clue, String word) {       
    	this.startTime = System.currentTimeMillis();
    	this.level = level;
        this.clue = clue;
        this.word = word;
        lifes = (byte) 3;
        this.alive = true;
        this.playedLevels.add(level);
        setLetters(word);
        scoreMultiplier = 30;
        
    }
    
    public void setScoreMultiplier( byte newScoreMultiplier) {
    	this.scoreMultiplier = newScoreMultiplier;
    }
    
    public byte getScoreMultiplier() {
    	return this.scoreMultiplier;
    }
    
    public void setLetters(String word) {
    	// buttons order
    	 char p1 = 0, p2 = 0, p3 = 0, p4 = 0, p5 = 0, p6 = 0, p7 = 0, p8 = 0, p9 = 0, p10 = 0, p11 = 0, p12 = 0;
    	 ArrayList<Character> positions = new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    	
    	// possible letter's dispositions
    	 ArrayList<Character> d1 = new ArrayList<>(Arrays.asList( p7, p3, p4, p8, p12, p11, p10, p9, p5, p1, p2, p6 ));
    	 ArrayList<Character> d2 = new ArrayList<>(Arrays.asList( p7, p8, p12, p11, p10, p9, p5, p6, p1, p2, p3, p4 ));
    	 ArrayList<Character> d3 = new ArrayList<>(Arrays.asList( p7, p11, p12, p8, p4, p3, p2, p6, p10, p9, p5, p1 ));
    	 ArrayList<Character> d4 = new ArrayList<>(Arrays.asList( p7, p3, p2, p1, p5, p6, p9, p10, p11, p12, p8, p4 ));
    	 ArrayList<Character> d5 = new ArrayList<>(Arrays.asList( p6, p2, p1, p5, p9, p10, p11, p12, p8, p4, p3, p7 ));
    	 ArrayList<Character> d6 = new ArrayList<>(Arrays.asList( p6, p5, p1, p2, p3, p4, p8, p7, p12, p11, p10, p9 ));
    	 ArrayList<Character> d7 = new ArrayList<>(Arrays.asList( p6, p5, p9, p10, p11, p12, p8, p7, p4, p3, p2, p1 ));
    	 ArrayList<Character> d8 = new ArrayList<>(Arrays.asList( p6, p10, p9, p5, p1, p2, p3, p4, p8, p12, p11, p7 ));
    	 ArrayList<Character> d9 = new ArrayList<>(Arrays.asList( p6, p10, p11, p12, p8, p4, p7, p3, p2, p1, p5, p9 ));
    	 ArrayList<Character> d10 = new ArrayList<>(Arrays.asList( p10, p9, p5, p6, p7, p11, p12, p8, p4, p3, p2, p1 ));
    	 ArrayList<Character> d11 = new ArrayList<>(Arrays.asList( p10, p11, p12, p8, p4, p3, p7, p6, p2, p1, p5, p9 ));
    	 ArrayList<Character> d12 = new ArrayList<>(Arrays.asList( p10, p6, p7, p11, p12, p8, p4, p3, p2, p1, p5, p9 ));
    	 ArrayList<Character> d13 = new ArrayList<>(Arrays.asList( p11, p12, p8, p4, p3, p7, p6, p2, p1, p5, p9, p10 ));
    	 ArrayList<Character> d14 = new ArrayList<>(Arrays.asList( p11, p7, p6, p10, p9, p5, p1, p2, p3, p4, p8, p12 ));
    	 ArrayList<Character> d15 = new ArrayList<>(Arrays.asList( p11, p10, p6, p7, p12, p8, p4, p3, p2, p1, p5, p9 ));
    	 ArrayList<Character> d16 = new ArrayList<>(Arrays.asList( p2, p1, p5, p9, p10, p11, p12, p8, p4, p3, p7, p6 ));
    	 ArrayList<Character> d17 = new ArrayList<>(Arrays.asList( p2, p1, p5, p6, p7, p3, p4, p8, p12, p11, p10, p9 ));
    	 ArrayList<Character> d18 = new ArrayList<>(Arrays.asList( p2, p3, p4, p8, p12, p11, p7, p6, p10, p9, p5, p1 ));
    	 ArrayList<Character> d19 = new ArrayList<>(Arrays.asList( p2, p3, p4, p8, p7, p6, p12, p11, p10, p9, p5, p1 ));
    	 ArrayList<Character> d20 = new ArrayList<>(Arrays.asList( p2, p6, p7, p3, p4, p8, p12, p11, p10, p9, p5, p1 ));
    	 ArrayList<Character> d21 = new ArrayList<>(Arrays.asList( p2, p6, p1, p5, p9, p10, p11, p12, p8, p4, p3, p7 ));
    	 ArrayList<Character> d22 = new ArrayList<>(Arrays.asList( p3, p4, p8, p12, p11, p7, p6, p10, p9, p5, p1, p2 ));
    	 ArrayList<Character> d23 = new ArrayList<>(Arrays.asList( p3, p4, p8, p7, p6, p2, p1, p5, p9, p10, p11, p12 ));
    	 ArrayList<Character> d24 = new ArrayList<>(Arrays.asList( p3, p4, p8, p7, p6, p2, p1, p5, p9, p10, p11, p12 ));
    	 ArrayList<Character> d25 = new ArrayList<>(Arrays.asList( p3, p7, p6, p2, p1, p5, p9, p10, p11, p12, p8, p4 ));
    	 ArrayList<Character> d26 = new ArrayList<>(Arrays.asList( p3, p7, p4, p8, p12, p11, p10, p9, p5, p1, p2, p6 ));
    	 
    	// set letters values to the positions 
        IntStream.range(0, word.length()).forEach(i -> {
                letters.add(word.charAt(i));
                positions.add(word.charAt(i));
        });	
        
        // select a random position for this level
        ArrayList<ArrayList> availablePositions = new ArrayList<>(Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25));
        byte randomPos = (byte) Math.round((Math.random() * availablePositions.size()));
        this.letters = availablePositions.get((randomPos));
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
