package com.Space.Game.Models;

import org.springframework.stereotype.Component;

@Component
public class GameDTO {
	private byte lifes;
	private int playedLevels;
	private Long score;
	private int level;
	
	public GameDTO() {
		this.lifes = 4;
		this.level = -1;
	}
	
	public void setLifes(byte newLifes) {
		this.lifes = newLifes;
	}
	
	public byte getLifes() {
		return this.lifes;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	

	public Long getScore() {
		return score;
	}
	
	public void setLevel(int newLevel) {
		this.level = newLevel;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setPlayedLevels(int updatePlayedLevels) {
		this.playedLevels = updatePlayedLevels;
	}
	
	public int getPlayedLevels() {
		return this.playedLevels;
	}
}