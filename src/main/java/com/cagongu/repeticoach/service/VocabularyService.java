package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.dto.request.CreateVocabularyRequest;
import com.cagongu.repeticoach.dto.request.UpdateVocabularyRequest;
import com.cagongu.repeticoach.dto.response.VocabularyDTO;
import com.cagongu.repeticoach.mapper.VocabularyMapper;
import com.cagongu.repeticoach.model.Topic;
import com.cagongu.repeticoach.model.Vocabulary;
import com.cagongu.repeticoach.model.VocabularyRecord;
import com.cagongu.repeticoach.repository.VocabularyRepository;
import com.cagongu.repeticoach.utils.ConvertCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabRepo;

    private final TopicService topicService;

    private final VocabularyMapper vocabularyMapper;

    public List<Vocabulary> findAll() {
        return vocabRepo.findAll();
    }

    public VocabularyDTO findById(Long id) {
        return vocabularyMapper.vocabularyToVocabularyDTO(vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found")));
    }

    public Vocabulary findByIdInternal(Long id) {
        return vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
    }

    public VocabularyDTO save(CreateVocabularyRequest req) {
        Topic topic = topicService.findById(req.getTopic());
        Vocabulary vocabulary = Vocabulary.builder()
                .word(req.getWord())
                .type(req.getType())
                .meaning(req.getMeaning())
                .pronunciation(req.getPronunciation())
                .topic(topic)
                .build();
        return vocabularyMapper.vocabularyToVocabularyDTO(vocabRepo.save(vocabulary));
    }

    public VocabularyDTO update(Long id, UpdateVocabularyRequest req) {
        Vocabulary updateWord = vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
        if (StringUtils.hasText(req.getTopic())) {
            Topic topic = topicService.findById(req.getTopic());
            updateWord.setTopic(topic);
        }

        if (StringUtils.hasText(req.getWord())) {
            updateWord.setWord(req.getWord());
        }
        if (StringUtils.hasText(req.getType())) {
            updateWord.setType(req.getType());
        }
        if (StringUtils.hasText(req.getPronunciation())) {
            updateWord.setPronunciation(req.getPronunciation());
        }
        if (StringUtils.hasText(req.getMeaning())) {
            updateWord.setMeaning(req.getMeaning());
        }

        return vocabularyMapper.vocabularyToVocabularyDTO(vocabRepo.save(updateWord));
    }

    public void deleteById(Long id) {
        Vocabulary vocabulary = vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
        vocabRepo.delete(vocabulary);
    }

    public Vocabulary findVocabularyByWordInternal(String word) {
        return vocabRepo.findByWord(word).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
    }

    public VocabularyDTO findVocabularyByWord(String word) {
        return vocabularyMapper.vocabularyToVocabularyDTO(vocabRepo.findByWord(word).orElseThrow(() -> new RuntimeException("Vocabulary not found")));
    }

    public void loadCsvData() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/vocabularies.csv");
        ConvertCSV cvt = new ConvertCSV();
        List<VocabularyRecord> recs = cvt.convertCSV(file, VocabularyRecord.class);

        recs.forEach(vocabularyRecord -> {
            Topic topic = topicService.findById(vocabularyRecord.getTopic());
            vocabRepo.save(Vocabulary.builder()
                    .word(vocabularyRecord.getWord())
                    .pronunciation(vocabularyRecord.getPronunciation())
                    .type(vocabularyRecord.getType())
                    .meaning(vocabularyRecord.getMeaning())
                    .topic(topic)
                    .build());
        });
    }
}
