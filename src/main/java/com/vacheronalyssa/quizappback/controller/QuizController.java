package com.vacheronalyssa.quizappback.controller;

import com.vacheronalyssa.quizappback.entity.Question;
import com.vacheronalyssa.quizappback.entity.Quiz;
import com.vacheronalyssa.quizappback.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    // Quiz CRUD Endpoints

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody Quiz quizDetails) {
        try {
            Quiz updatedQuiz = quizService.updateQuiz(id, quizDetails);
            return ResponseEntity.ok(updatedQuiz);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    // Question CRUD Endpoints

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long quizId) {
        try {
            List<Question> questions = quizService.getQuestionsByQuizId(quizId);
            return ResponseEntity.ok(questions);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<Quiz> addQuestionToQuiz(@PathVariable Long quizId, @RequestBody Question question) {
        try {
            Quiz updatedQuiz = quizService.addQuestionToQuiz(quizId, question);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedQuiz);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Void> removeQuestionFromQuiz(@PathVariable Long quizId, @PathVariable Long questionId) {
        try {
            quizService.removeQuestionFromQuiz(quizId, questionId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Standalone Question Endpoints

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return quizService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question questionDetails) {
        try {
            Question updatedQuestion = quizService.updateQuestion(id, questionDetails);
            return ResponseEntity.ok(updatedQuestion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
