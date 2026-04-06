package com.vacheronalyssa.quizappback.service;

import com.vacheronalyssa.quizappback.entity.Question;
import com.vacheronalyssa.quizappback.entity.Quiz;
import com.vacheronalyssa.quizappback.repository.QuestionRepository;
import com.vacheronalyssa.quizappback.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    // Quiz CRUD Operations
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz updateQuiz(Long id, Quiz quizDetails) {
        return quizRepository.findById(id).map(quiz -> {
            quiz.setTitle(quizDetails.getTitle());
            quiz.setQuestions(quizDetails.getQuestions());
            return quizRepository.save(quiz);
        }).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    // Question CRUD Operations
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id).map(question -> {
            question.setText(questionDetails.getText());
            question.setOptions(questionDetails.getOptions());
            question.setCorrectAnswer(questionDetails.getCorrectAnswer());
            return questionRepository.save(question);
        }).orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    // Quiz-Question Operations
    public Quiz addQuestionToQuiz(Long quizId, Question question) {
        return quizRepository.findById(quizId).map(quiz -> {
            Question savedQuestion = questionRepository.save(question);
            quiz.getQuestions().add(savedQuestion);
            return quizRepository.save(quiz);
        }).orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }

    public void removeQuestionFromQuiz(Long quizId, Long questionId) {
        quizRepository.findById(quizId).ifPresent(quiz -> {
            quiz.getQuestions().removeIf(q -> q.getId().equals(questionId));
            quizRepository.save(quiz);
            questionRepository.deleteById(questionId);
        });
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        return quizRepository.findById(quizId)
                .map(Quiz::getQuestions)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }
}
