package com.cagongu.repeticoach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @Builder.Default
    private int quality = 0;
    @JsonIgnore
    @ManyToOne
    private Topic topic;

    public void setTopic(Topic topic) {
        this.topic = topic;
        topic.getVocabularyList().add(this);
    }

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "vocabulary", cascade =  CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions = new HashSet<>();
}
