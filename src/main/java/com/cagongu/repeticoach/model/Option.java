package com.cagongu.repeticoach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private String content;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "question_id")
    private Question question;

    public void setQuestion(Question question){
        this.question = question;
        question.getOptions().add(this);
    }
}
