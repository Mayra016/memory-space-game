package com.Guess.Word;

import org.junit.Test;
import org.springframework.ui.Model;
import com.Guess.Word.Controllers.GuessWordController;

public class GuessWordControllerTest {
	GuessWordController controller;
	Model model;
	@Test
	public void levelTest() {		
		controller.home((long)2, model);
	}
}
