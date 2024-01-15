package com.Guess.Word.Interfaces;

import java.util.ArrayList;
import java.util.List;

public interface GuessWordI {

	String getClue();
	void setClue(String clue);
	
	String getWord();
	void setWord(String word);
	
	long getStartTime();
	void setStartTime();

	void setLifes(int lifes);
	byte getLifes();

	Long getLevel();
	void setLevel(Long level);

	List<Long> getPlayedLevels();
	void setPlayedLevels(List<Long> levels);
	
	void setScoreMultiplier(byte newScoreMultiplier);
	byte getScoreMultiplier();

	void setAlive(boolean newAlive);
	boolean getAlive();

	int getScore();
	void setScore(int newScore);
	
	ArrayList<Character> getLetters();
	void setLetters(ArrayList<Character> arrayList);
	void setUserInput(String letterToString);
	String getUserInput();
	
}