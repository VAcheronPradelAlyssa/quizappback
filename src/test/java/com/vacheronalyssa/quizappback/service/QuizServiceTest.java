package com.vacheronalyssa.quizappback.service;

import com.vacheronalyssa.quizappback.entity.Question;
import com.vacheronalyssa.quizappback.entity.Quiz;
import com.vacheronalyssa.quizappback.repository.QuestionRepository;
import com.vacheronalyssa.quizappback.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz testQuiz;
    private Question testQuestion;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testQuiz = new Quiz();
        testQuiz.setId(1L);
        testQuiz.setTitle("Java Basics Quiz");
        testQuiz.setQuestions(new ArrayList<>());

        testQuestion = new Question();
        testQuestion.setId(1L);
        testQuestion.setText("What is Java?");
        testQuestion.setOptions(List.of("Programming Language", "Coffee", "Island", "Framework"));
        testQuestion.setAnswer("Programming Language");
    }

    // Quiz CRUD Tests

    @Test
    void testCreateQuiz() {
        when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

        Quiz createdQuiz = quizService.createQuiz(testQuiz);

        assertNotNull(createdQuiz);
        assertEquals("Java Basics Quiz", createdQuiz.getTitle());
        verify(quizRepository, times(1)).save(testQuiz);
    }

    @Test
    void testGetQuizById() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));

        Optional<Quiz> foundQuiz = quizService.getQuizById(1L);

        assertTrue(foundQuiz.isPresent());
        assertEquals("Java Basics Quiz", foundQuiz.get().getTitle());
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuizById_NotFound() {
        when(quizRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Quiz> foundQuiz = quizService.getQuizById(999L);

        assertTrue(foundQuiz.isEmpty());
        verify(quizRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllQuizzes() {
        List<Quiz> quizzes = List.of(testQuiz);
        when(quizRepository.findAll()).thenReturn(quizzes);

        List<Quiz> foundQuizzes = quizService.getAllQuizzes();

        assertEquals(1, foundQuizzes.size());
        assertEquals("Java Basics Quiz", foundQuizzes.get(0).getTitle());
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void testUpdateQuiz() {
        Quiz updatedDetails = new Quiz();
        updatedDetails.setTitle("Advanced Java Quiz");
        updatedDetails.setQuestions(new ArrayList<>());

        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

        Quiz updatedQuiz = quizService.updateQuiz(1L, updatedDetails);

        assertNotNull(updatedQuiz);
        assertEquals("Advanced Java Quiz", updatedQuiz.getTitle());
        verify(quizRepository, times(1)).findById(1L);
        verify(quizRepository, times(1)).save(testQuiz);
    }

    @Test
    void testUpdateQuiz_NotFound() {
        when(quizRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> quizService.updateQuiz(999L, testQuiz));
        verify(quizRepository, times(1)).findById(999L);
        verify(quizRepository, never()).save(any());
    }

    @Test
    void testDeleteQuiz() {
        quizService.deleteQuiz(1L);

        verify(quizRepository, times(1)).deleteById(1L);
    }

    // Question CRUD Tests

    @Test
    void testCreateQuestion() {
        when(questionRepository.save(any(Question.class))).thenReturn(testQuestion);

        Question createdQuestion = quizService.createQuestion(testQuestion);

        assertNotNull(createdQuestion);
        assertEquals("What is Java?", createdQuestion.getText());
        verify(questionRepository, times(1)).save(testQuestion);
    }

    @Test
    void testGetQuestionById() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(testQuestion));

        Optional<Question> foundQuestion = quizService.getQuestionById(1L);

        assertTrue(foundQuestion.isPresent());
        assertEquals("What is Java?", foundQuestion.get().getText());
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = List.of(testQuestion);
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> foundQuestions = quizService.getAllQuestions();

        assertEquals(1, foundQuestions.size());
        assertEquals("What is Java?", foundQuestions.get(0).getText());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void testUpdateQuestion() {
        Question updatedDetails = new Question();
        updatedDetails.setText("What is Spring Framework?");
        updatedDetails.setOptions(List.of("Framework", "Language", "Database", "Server"));
        updatedDetails.setAnswer("Framework");

        when(questionRepository.findById(1L)).thenReturn(Optional.of(testQuestion));
        when(questionRepository.save(any(Question.class))).thenReturn(testQuestion);

        Question updatedQuestion = quizService.updateQuestion(1L, updatedDetails);

        assertNotNull(updatedQuestion);
        verify(questionRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).save(testQuestion);
    }

    @Test
    void testDeleteQuestion() {
        quizService.deleteQuestion(1L);

        verify(questionRepository, times(1)).deleteById(1L);
    }

    // Quiz-Question Operations Tests

    @Test
    void testAddQuestionToQuiz() {
        testQuiz.setQuestions(new ArrayList<>());
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
        when(questionRepository.save(any(Question.class))).thenReturn(testQuestion);
        when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

        Quiz updatedQuiz = quizService.addQuestionToQuiz(1L, testQuestion);

        assertNotNull(updatedQuiz);
        verify(quizRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).save(testQuestion);
        verify(quizRepository, times(1)).save(testQuiz);
    }

    @Test
    void testAddQuestionToQuiz_QuizNotFound() {
        when(quizRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> quizService.addQuestionToQuiz(999L, testQuestion));
        verify(quizRepository, times(1)).findById(999L);
    }

    @Test
    void testRemoveQuestionFromQuiz() {
        testQuiz.setQuestions(new ArrayList<>(List.of(testQuestion)));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));
        when(quizRepository.save(any(Quiz.class))).thenReturn(testQuiz);

        quizService.removeQuestionFromQuiz(1L, 1L);

        verify(quizRepository, times(1)).findById(1L);
        verify(quizRepository, times(1)).save(testQuiz);
        verify(questionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetQuestionsByQuizId() {
        testQuiz.setQuestions(List.of(testQuestion));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(testQuiz));

        List<Question> questions = quizService.getQuestionsByQuizId(1L);

        assertEquals(1, questions.size());
        assertEquals("What is Java?", questions.get(0).getText());
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuestionsByQuizId_QuizNotFound() {
        when(quizRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> quizService.getQuestionsByQuizId(999L));
        verify(quizRepository, times(1)).findById(999L);
    }
}
