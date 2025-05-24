package com.cagongu.repeticoach.dto.request;

import com.cagongu.repeticoach.model.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuestionRequest {
    private String type;
    private String question;
    private String correct_answer;
    private String explanation;
    private Set<Option> options;
}