package com.Guess.Word.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Guess.Word.Entities.GuessWordPT;

@Repository
public interface GuessWordPTRepository extends JpaRepository<GuessWordPT, Long> {
	
}