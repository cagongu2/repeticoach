package com.cagongu.repeticoach.dto.response;

import com.cagongu.repeticoach.model.Question;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyDTO {
    private Long id;
    private String word;
    private String pronunciation;
    private String type;
    private String meaning;
    private float ef;
    private int repetition;
    private int intervalDays;
    private LocalDate lastReview;
    private LocalDate nextReview;
    private int quality ;
    private String topicName;
    private Set<Question> questions;
}
