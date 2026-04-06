package com.vacheronalyssa.quizappback.repository;

import com.vacheronalyssa.quizappback.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
