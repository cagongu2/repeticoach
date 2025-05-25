package com.cagongu.repeticoach.dto.response.OpenAI;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {
    private List<OpenAIChatDTO> questions;
}