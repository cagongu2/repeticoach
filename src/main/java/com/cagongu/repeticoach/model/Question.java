package com.cagongu.repeticoach.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String question;
    private String correct_answer;
    private String explanation;

    @Builder.Default
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Option> options = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "vocabulary_id")
    private Vocabulary vocabulary;

    public void setVocabulary(Vocabulary vocabulary){
        this.vocabulary = vocabulary;
        vocabulary.getQuestions().add(this);
    }
}
