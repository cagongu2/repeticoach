package com.cagongu.repeticoach.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyRecord {
    @CsvBindByName
    private String word;
    @CsvBindByName
    private String pronunciation;
    @CsvBindByName
    private String type;
    @CsvBindByName
    private String meaning;
    @CsvBindByName
    private String topic;
}
