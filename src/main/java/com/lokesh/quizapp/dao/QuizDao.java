package com.lokesh.quizapp.dao;

import com.lokesh.quizapp.modal.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
    Optional<Quiz> findAllById(Integer id);
}
