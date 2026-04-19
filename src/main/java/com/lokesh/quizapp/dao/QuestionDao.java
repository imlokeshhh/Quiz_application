package com.lokesh.quizapp.dao;

import com.lokesh.quizapp.modal.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    List<Question> findByDifficultyLevel(String difficultyLevel);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :Qnum",nativeQuery = true)
    List<Question> findRandomQuestionByCategory(@Param("category") String category, @Param("Qnum") int Qnum);
}
