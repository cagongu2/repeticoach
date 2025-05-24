package com.cagongu.repeticoach.dto.response;

import com.cagongu.repeticoach.model.Option;
import com.cagongu.repeticoach.model.Vocabulary;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private String type;
    private String question;
    private String correct_answer;
    private String explanation;
    @Builder.Default
    @OneToMany
    private Set<Option> options = new HashSet<>();
    private String word;
}
