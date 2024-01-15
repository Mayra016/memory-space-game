package com.Guess.Word.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Guess.Word.Entities.GuessWord;

@Repository
public interface GuessWordRepository extends JpaRepository<GuessWord, Long> {
	
}