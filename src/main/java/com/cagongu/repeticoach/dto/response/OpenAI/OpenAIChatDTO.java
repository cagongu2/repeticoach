package com.cagongu.repeticoach.dto.response.OpenAI;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAIChatDTO {
    private String word;
    private String type;
    private String question;
    private List<OptionDTO> option;
    private String correct_answer;
    private String explanation;
}
