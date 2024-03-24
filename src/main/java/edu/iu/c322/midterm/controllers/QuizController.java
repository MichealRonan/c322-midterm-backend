package edu.iu.c322.midterm.controllers;

import edu.iu.c322.midterm.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.iu.c322.midterm.repository.FileRepository;


import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quizzes")
public class QuizController {



    private final FileRepository fileRepository;

    public QuizController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public ResponseEntity<Integer> addQuiz(@RequestBody Quiz quiz) {
        try {
            int id = fileRepository.addQuiz(quiz);
            return ResponseEntity.ok(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        try {
            List<Quiz> quizzes = fileRepository.findAllQuizzes();
            return ResponseEntity.ok(quizzes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
        try {
            List<Quiz> quizzes = fileRepository.findAllQuizzes();
            return quizzes.stream()
                    .filter(quiz -> quiz.getId() == id)
                    .findFirst()
                    .map(quiz -> ResponseEntity.ok(quiz))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuiz(@PathVariable int id, @RequestBody Quiz quiz) {
        try {
            quiz.setId(id);

            boolean updated = fileRepository.updateQuiz(quiz);
            if (updated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
