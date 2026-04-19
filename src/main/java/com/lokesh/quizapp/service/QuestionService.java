package com.lokesh.quizapp.service;

import com.lokesh.quizapp.modal.Question;
import com.lokesh.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByDifficulty(String difficultyLevel) {
        try {
            return new ResponseEntity<>(questionDao.findByDifficultyLevel(difficultyLevel),HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Add Question",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to Delete Question",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(int id, Question newquestion) {
        try{
            if(questionDao.existsById(id)){
                Question existingQuestion = questionDao.findById(id).get();

                existingQuestion.setCategory(newquestion.getCategory());
                existingQuestion.setDifficultyLevel(newquestion.getDifficultyLevel());
                existingQuestion.setQuestionTitle(newquestion.getQuestionTitle());
                existingQuestion.setOption1(newquestion.getOption1());
                existingQuestion.setOption2(newquestion.getOption2());
                existingQuestion.setOption3(newquestion.getOption3());
                existingQuestion.setOption4(newquestion.getOption4());
                existingQuestion.setCorrectAnswer(newquestion.getCorrectAnswer());

                questionDao.save(existingQuestion);
                return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error updating Question",HttpStatus.BAD_REQUEST);
    }
}
