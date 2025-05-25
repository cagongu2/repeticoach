package com.cagongu.repeticoach.dto.request.OpenAI;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateQuizRequest {
    List<String> words;
}
