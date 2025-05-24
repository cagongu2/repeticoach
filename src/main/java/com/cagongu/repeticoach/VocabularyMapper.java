package com.cagongu.repeticoach;

import com.cagongu.repeticoach.dto.response.VocabularyDTO;
import com.cagongu.repeticoach.model.Vocabulary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VocabularyMapper {
    @Mapping(source = "topic.name", target = "topicName")
    VocabularyDTO vocabularyToVocabularyDTO(Vocabulary vocabulary);
}
