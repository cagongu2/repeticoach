package com.cagongu.repeticoach.controller;

import com.cagongu.repeticoach.dto.request.CreateQuestionRequest;
import com.cagongu.repeticoach.dto.request.GetDailyQuestionRequest;
import com.cagongu.repeticoach.dto.request.UpdateQuestionRequest;
import com.cagongu.repeticoach.dto.response.QuestionDTO;
import com.cagongu.repeticoach.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping()
    public List<QuestionDTO> getAllQuestions(){
        return questionService.getAllQuestion();
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @PostMapping()
    public QuestionDTO createNewQuestion(@RequestBody CreateQuestionRequest request){
        return questionService.createNewQuestion(request);
    }

    @PutMapping("/{id}")
    public QuestionDTO updateQuestion(@PathVariable Long id, @RequestBody UpdateQuestionRequest request){
        return questionService.updateQuestion(id, request);
    }

    @PostMapping("/import-data")
    public void loadCsvData() throws FileNotFoundException {
        questionService.loadCsvData();
    }
}
