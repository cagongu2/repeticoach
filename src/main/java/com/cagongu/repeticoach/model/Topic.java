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
public class Topic {
    @Id
    private String name;
    @Builder.Default
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private Set<Vocabulary> vocabularyList = new HashSet<>();
}
