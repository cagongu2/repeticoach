package com.cagongu.repeticoach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String pronunciation;
    private String type;
    private String meaning;

    @Builder.Default
    private float ef = 2.5f;
    @Builder.Default
    private int repetition = 0;
    @Builder.Default
    private int intervalDays = 0;
    private LocalDate lastReview;
    private LocalDate nextReview;
    @JsonIgnore
    @ManyToOne
    private Topic topic;
}
