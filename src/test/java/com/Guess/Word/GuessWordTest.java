package com.Guess.Word;

import org.junit.jupiter.api.Test;
import com.Guess.Word.Entities.*;


public class GuessWordTest {
	GuessWord level = new GuessWord((long)1, "Cuando ya no te puedes fiar", "Desconfianza");
	
	
	@Test
	public void setLettersTest() {
		System.out.println(level.getLetters());
	}
}
