package com.cagongu.repeticoach.dto.request.OpenAI;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIMessageRequest {
    @Builder.Default
    private String role = "user";
    private String content;
}
