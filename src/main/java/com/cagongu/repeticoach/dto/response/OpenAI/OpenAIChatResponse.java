package com.cagongu.repeticoach.dto.response.OpenAI;

import lombok.*;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
