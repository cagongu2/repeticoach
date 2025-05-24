package com.cagongu.repeticoach.dto.request;

import com.cagongu.repeticoach.model.Option;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequest {
    @NotBlank
    private String type;
    @NotBlank
    private String question;
    @NotBlank
    private String correct_answer;
    @NotBlank
    private String explanation;
    @NotEmpty
    private Set<Option> options;
    @NotBlank
    private String word;
}
