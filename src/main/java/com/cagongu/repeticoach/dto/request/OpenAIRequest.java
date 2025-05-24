package com.cagongu.repeticoach.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIRequest {
    @Builder.Default
    private String model = "gpt-4.1";
    private OpenAIMessageRequest request;
}
