package com.cagongu.repeticoach.dto.request.OpenAI;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIChatRequest {
    @Builder.Default
    private String model = "gpt-4.1";
    private List<OpenAIMessageRequest> messages;
}
