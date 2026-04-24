package com.Quiz.QuizLeader.controller;

import com.Quiz.QuizLeader.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz")
    public Map<String, Object> run() throws InterruptedException {
        return quizService.run();
    }
}
