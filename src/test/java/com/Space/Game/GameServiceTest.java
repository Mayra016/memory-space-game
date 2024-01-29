package com.Space.Game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import com.Space.Game.Services.GameService;

public class GameServiceTest {

	GameService service = new GameService();
	
	@Test
	public void compareAnswerTest() {
		List<String> levelMoves = new ArrayList<>();
		List<String> userMoves = new ArrayList<>();
		
		levelMoves.add("green");
		levelMoves.add("yellow");
		levelMoves.add("red");
		
		userMoves.add("green");
		userMoves.add("yellow");
		userMoves.add("red");
		
		AtomicBoolean compare = service.compareAnswer(levelMoves, userMoves);
		AtomicBoolean result = new AtomicBoolean(true);
		System.out.println(compare);
		assertEquals(true, compare.get());
	}
	
	@Test
	public void compareFalseAnswerTest() {
		List<String> levelMoves = new ArrayList<>();
		List<String> userMoves = new ArrayList<>();
		
		levelMoves.add("green");
		levelMoves.add("yellow");
		levelMoves.add("red");
		
		userMoves.add("green");
		userMoves.add("purple");
		userMoves.add("red");
		
		AtomicBoolean compare = service.compareAnswer(levelMoves, userMoves);
		AtomicBoolean result = new AtomicBoolean(true);
		System.out.println(compare);
		assertEquals(false, compare.get());
	}
	
	@Test
	public void compareNullAnswerTest() {
		List<String> levelMoves = new ArrayList<>();
		List<String> userMoves = new ArrayList<>();
		
		levelMoves.add("green");
		levelMoves.add("yellow");
		levelMoves.add("red");
		
		
		AtomicBoolean compare = service.compareAnswer(levelMoves, userMoves);
		AtomicBoolean result = new AtomicBoolean(true);
		System.out.println(compare);
		assertEquals(false, compare.get());
	}
}
