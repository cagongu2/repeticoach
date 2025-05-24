package com.cagongu.repeticoach.controller;

import com.cagongu.repeticoach.dto.request.CreateVocabularyRequest;
import com.cagongu.repeticoach.dto.request.UpdateVocabularyRequest;
import com.cagongu.repeticoach.dto.response.VocabularyDTO;
import com.cagongu.repeticoach.model.Vocabulary;
import com.cagongu.repeticoach.service.SpacedRepetitionService;
import com.cagongu.repeticoach.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/vocabularies")
@RequiredArgsConstructor
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping()
    public List<Vocabulary> getAllVocabularies() {
        return vocabularyService.findAll();
    }

    @GetMapping("/{id}")
    public VocabularyDTO getVocabularyById(@PathVariable Long id) {
        return vocabularyService.findById(id);
    }

    @PostMapping()
    public VocabularyDTO createVocabulary(@RequestBody CreateVocabularyRequest vocabulary) {
        return vocabularyService.save(vocabulary);
    }

    @PutMapping("/{id}")
    public VocabularyDTO updateVocabulary(@PathVariable Long id, @RequestBody UpdateVocabularyRequest vocabulary) {

        return vocabularyService.update(id, vocabulary);
    }

    @DeleteMapping("/{id}")
    public void deleteVocabulary(@PathVariable Long id) {
        vocabularyService.deleteById(id);
    }

    @PostMapping("/import-data")
    public void loadCsvData() throws FileNotFoundException {
        vocabularyService.loadCsvData();

    }
}

