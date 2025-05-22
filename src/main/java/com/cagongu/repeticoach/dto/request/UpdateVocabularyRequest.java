package com.cagongu.repeticoach.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVocabularyRequest {
    private String word;
    private String pronunciation;
    private String type;
    private String meaning;
    private String topic;
}
