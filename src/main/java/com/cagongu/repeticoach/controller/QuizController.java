package com.cagongu.repeticoach.controller;

import com.cagongu.repeticoach.dto.request.OpenAI.GenerateQuizRequest;
import com.cagongu.repeticoach.dto.response.OpenAI.QuizResponse;
import com.cagongu.repeticoach.service.OpenAiChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController  {
    private final OpenAiChatService openAiChatService;

    @PostMapping("/quiz")
    public QuizResponse generateQuiz(@RequestBody GenerateQuizRequest request) throws JsonProcessingException {
        return openAiChatService.generateQuestionQuiz(request.getWords());
    }
}
