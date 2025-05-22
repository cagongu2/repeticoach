package com.cagongu.repeticoach.dto.request;

import com.cagongu.repeticoach.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVocabularyRequest {
    private String word;
    private String pronunciation;
    private String type;
    private String meaning;
    private String topic;
}
