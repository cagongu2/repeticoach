package com.cagongu.repeticoach.mapper;

import com.cagongu.repeticoach.dto.response.QuestionDTO;
import com.cagongu.repeticoach.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QuestionMapper {
    @Mapping(source = "vocabulary.word", target = "word")
    QuestionDTO questionToQuestionDTO(Question question);
}
