package com.Space.Game.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameModel {
	private List<String> posibleMoves = new ArrayList<>(Arrays.asList("green", "yellow", "red", "blue"));
	private List<String> playerMoves = new ArrayList<>();
	private List<String> levelMoves = new ArrayList<>();
	private int level;
    private byte lifes;
    private boolean alive;
    private Long score;
    private long startTime;
    private byte scoreMultiplier;
	
	public GameModel() {
		setLevelMoves();
		this.level = 0;
		this.lifes = 3;
		this.alive = true;
		this.score = 0L;
	}
	
	public void setPlayerMoves(String move) {
		this.playerMoves.add(move);		
	}
	
	public List<String> getPlayerMoves() {
		return this.playerMoves;
	}

	public void setLevelMoves() {
		Random generator = new Random();
		
		while ( this.levelMoves.size()< 3 + this.level ) {
			byte index = (byte) generator.nextInt(0,3);
			levelMoves.add(posibleMoves.get(index));			
		}	
	}
	
	public void updateLevelMoves() {
		Random generator = new Random();
		
		byte index = (byte) generator.nextInt(0,3);
		levelMoves.add(posibleMoves.get(index));
		
	}
	
	public List<String> getLevelMoves() {
		return this.levelMoves;
	}
	
	public void setLevel() {
		this.level++;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLifes(byte newLifes) {
		this.lifes=newLifes;
	}
	
	public byte getLifes() {
		return this.lifes;
	}

	public void setAlive(boolean newState) {
		this.alive = newState;
	}
	
	public boolean getAlive() {
		return this.alive;
	}
    
	public void setScore(byte newScore) {
		this.score = score + newScore;
	}
	
	public Long getScore() {
		return this.score;
	}
	
    public void setStartTime() {
    	this.startTime = System.currentTimeMillis();
    }
    
    public Long getStartTime() {
    	return this.startTime;
    }

    public void setScoreMultiplier(byte newMultiplier) {
    	this.scoreMultiplier = newMultiplier;
    }

    public byte getScoreMultiplier() {
    	return this.scoreMultiplier;
    }
}