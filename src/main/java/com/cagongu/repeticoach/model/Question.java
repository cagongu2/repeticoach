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
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Option> options = new HashSet<>();
    @ManyToOne
    private Vocabulary vocabulary;
}
