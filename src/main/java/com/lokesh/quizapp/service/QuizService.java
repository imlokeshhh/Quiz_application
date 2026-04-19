package com.lokesh.quizapp.service;

import com.lokesh.quizapp.dao.QuestionDao;
import com.lokesh.quizapp.dao.QuizDao;
import com.lokesh.quizapp.modal.Question;
import com.lokesh.quizapp.modal.QuestionWrapper;
import com.lokesh.quizapp.modal.Quiz;
import com.lokesh.quizapp.modal.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int Qnum, String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(category,Qnum);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz = quizDao.findAllById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questionsFromDb){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }


        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int correct = 0;

        for (Response response : responses) {
            for (Question question : questions) {

                if (question.getId() == response.getId() &&
                        response.getResponse().trim().equalsIgnoreCase(
                                question.getCorrectAnswer().trim())) {

                    correct++;
                }
            }
        }

        return new ResponseEntity<>(correct, HttpStatus.OK);
    }
}
