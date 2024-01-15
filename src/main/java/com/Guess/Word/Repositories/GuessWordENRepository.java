package com.Guess.Word.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Guess.Word.Entities.GuessWordEN;

@Repository
public interface GuessWordENRepository extends JpaRepository<GuessWordEN, Long> {
	
}