package com.Guess.Word.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Guess.Word.Entities.GuessWordDE;

@Repository
public interface GuessWordDERepository extends JpaRepository<GuessWordDE, Long> {
	
}