package com.cagongu.repeticoach.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRecord {
    @CsvBindByName
    private String word;
    @CsvBindByName
    private String type;
    @CsvBindByName
    private String question;
    @CsvBindByName
    private String correct_answer;
    @CsvBindByName
    private String explanation;
    @CsvBindByName
    private String option1;
    @CsvBindByName
    private String option2;
    @CsvBindByName
    private String option3;
    @CsvBindByName
    private String option4;
}
