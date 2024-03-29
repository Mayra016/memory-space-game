package com.Guess.Word.Entities;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GuessWordDTO {
	private byte lifes;
	private List<Long> playedLevels;
	private int score;
	private Long level;
	
	public GuessWordDTO() {
		this.lifes = 4;
		this.level = (long) -1;
	}
	
	public void setLifes(byte newLifes) {
		this.lifes = newLifes;
	}
	
	public byte getLifes() {
		return this.lifes;
	}
	
	// Add all previous levels to played levels
	public void setPlayedLevels(List<Long> newPlayedLevels, Long previousLevel) {
		this.playedLevels = newPlayedLevels;
		this.playedLevels.add(previousLevel);
	}

	public List<Long> getPlayedLevels() {
		return this.playedLevels;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setLevel(Long newLevel) {
		this.level = newLevel;
	}
	
	public Long getLevel() {
		return this.level;
	}
}