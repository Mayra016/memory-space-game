package com.Space.Game;

import org.junit.jupiter.api.Test;
import com.Space.Game.Models.GameModel;


public class GameModelTest {
	GameModel game = new GameModel();
	
	
	@Test
	public void setLevelMovesTest() {
		game.setLevelMoves();
		System.out.println(game.getLevelMoves());
		
		game.setLevel();
		game.setLevel();
		game.setLevelMoves();
		System.out.println(game.getLevelMoves());
	}
}
