package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.dto.request.CreateVocabularyRequest;
import com.cagongu.repeticoach.dto.request.UpdateVocabularyRequest;
import com.cagongu.repeticoach.model.Topic;
import com.cagongu.repeticoach.model.Vocabulary;
import com.cagongu.repeticoach.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabRepo;

    private final TopicService topicService;

    private final SpacedRepetitionService srService;

    public List<Vocabulary> findAll() {
        return vocabRepo.findAll();
    }

    public Vocabulary findById(Long id) {
        return vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
    }

    public Vocabulary save(CreateVocabularyRequest req) {
        Topic topic = topicService.findById(req.getTopic());
        Vocabulary vocabulary = Vocabulary.builder()
                .word(req.getWord())
                .type(req.getType())
                .meaning(req.getMeaning())
                .pronunciation(req.getPronunciation())
                .topic(topic)
                .build();
        return vocabRepo.save(vocabulary);
    }

    public Vocabulary update(Long id, UpdateVocabularyRequest req){
        Vocabulary updateWord = vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
        if(StringUtils.hasText(req.getTopic())){
            Topic topic = topicService.findById(req.getTopic());
            updateWord.setTopic(topic);
        }

        if(StringUtils.hasText(req.getWord())){
            updateWord.setWord(req.getWord());
        }
        if(StringUtils.hasText(req.getType())){
            updateWord.setType(req.getType());
        }
        if(StringUtils.hasText(req.getPronunciation())){
            updateWord.setPronunciation(req.getPronunciation());
        }
        if(StringUtils.hasText(req.getMeaning())){
            updateWord.setMeaning(req.getMeaning());
        }

        return vocabRepo.save(updateWord);
    }

    public void deleteById(Long id) {
        Vocabulary vocabulary = vocabRepo.findById(id).orElseThrow(() -> new RuntimeException("Vocabulary not found"));
        vocabRepo.delete(vocabulary);
    }

    public List<Vocabulary> getReviewWordsToday() {
        return vocabRepo.findByNextReviewLessThanEqual(LocalDate.now());
    }

    public List<Vocabulary> getReviewWordsToday(String topic) {
        return vocabRepo.findByNextReviewLessThanEqual(LocalDate.now())
                .stream()
                .filter(vocabulary -> Objects.equals(vocabulary.getTopic().getName(), topic))
                .toList();
    }


    public Vocabulary reviewWord(Long id, int quality) {
        Vocabulary vocab = vocabRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vocabulary not found: " + id));
        srService.updateReview(vocab, quality);
        return vocabRepo.save(vocab);
    }
}
