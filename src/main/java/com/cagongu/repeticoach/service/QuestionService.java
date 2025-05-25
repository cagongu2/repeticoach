package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.dto.request.CreateQuestionRequest;
import com.cagongu.repeticoach.dto.request.GetDailyQuestionRequest;
import com.cagongu.repeticoach.dto.request.UpdateQuestionRequest;
import com.cagongu.repeticoach.dto.response.QuestionDTO;
import com.cagongu.repeticoach.mapper.QuestionMapper;
import com.cagongu.repeticoach.model.*;
import com.cagongu.repeticoach.repository.OptionRepository;
import com.cagongu.repeticoach.repository.QuestionRepository;
import com.cagongu.repeticoach.utils.ConvertCSV;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final VocabularyService vocabularyService;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final EnglishLearningService englishLearningService;
    private final OptionRepository optionRepository;


    public QuestionDTO getQuestionById(Long id) {
        return questionMapper.questionToQuestionDTO(questionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found")));
    }

    public List<QuestionDTO> getAllQuestion() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::questionToQuestionDTO
                ).toList();
    }

    public List<QuestionDTO> getDailyQuestion(GetDailyQuestionRequest req) {
        List<QuestionDTO> dailyQuestionList;

        if (StringUtils.hasText(req.getType())) {
            dailyQuestionList = new ArrayList<>(
                    englishLearningService.getDailyWords(req.getTopic())
                            .stream()
                            .flatMap(vocabularyDTO -> vocabularyDTO.getQuestions()
                                    .stream()
                                    .filter(question -> question.getType().equals(req.getType()))
                                    .map(questionMapper::questionToQuestionDTO))
                            .toList()
            );
        } else {
            dailyQuestionList = new ArrayList<>(
                    englishLearningService.getDailyWords(req.getTopic())
                            .stream()
                            .flatMap(vocabularyDTO -> vocabularyDTO.getQuestions()
                                    .stream()
                                    .map(questionMapper::questionToQuestionDTO))
                            .toList()
            );
        }

        if (dailyQuestionList.size() <= req.getTotal()) {
            return dailyQuestionList;
        }

        Collections.shuffle(dailyQuestionList);
        return dailyQuestionList.subList(0, req.getTotal());
    }

    public QuestionDTO createNewQuestion(CreateQuestionRequest req) {
        Vocabulary vocabulary = vocabularyService.findVocabularyByWordInternal(req.getWord());

        Question question = Question.builder()
                .type(req.getType())
                .question(req.getQuestion())
                .correct_answer(req.getCorrect_answer())
                .explanation(req.getExplanation())
                .options(req.getOptions())
                .vocabulary(vocabulary)
                .build();

        questionRepository.save(question);
        return questionMapper.questionToQuestionDTO(question);
    }

    public QuestionDTO updateQuestion(Long id, UpdateQuestionRequest req) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new RuntimeException("Question not found"));

        if (StringUtils.hasText(req.getType())) {
            question.setType(req.getType());
        }
        if (StringUtils.hasText(req.getQuestion())) {
            question.setQuestion(req.getQuestion());
        }
        if (StringUtils.hasText((req.getCorrect_answer()))) {
            question.setCorrect_answer(req.getCorrect_answer());
        }
        if (StringUtils.hasText((req.getExplanation()))) {
            question.setExplanation(req.getExplanation());
        }
        if (!req.getOptions().isEmpty()) {
            question.getOptions().forEach(opt -> {
                req.getOptions().stream()
                        .filter(o -> o.getId().equals(opt.getId()))
                        .findFirst().ifPresent(reqOpt -> opt.setContent(reqOpt.getContent()));
            });
        }

        questionRepository.save(question);
        return questionMapper.questionToQuestionDTO(question);
    }

    public void loadCsvData() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/questions.csv");
        ConvertCSV cvts = new ConvertCSV();
        List<QuestionRecord> recs = cvts.convertCSV(file, QuestionRecord.class);
        recs.forEach(questionRecord -> {
            Vocabulary vocabulary = vocabularyService.findVocabularyByWordInternal(questionRecord.getWord());

            Question question = questionRepository.save(Question.builder()
                    .type(questionRecord.getType())
                    .question(questionRecord.getQuestion())
                    .correct_answer(questionRecord.getCorrect_answer())
                    .explanation(questionRecord.getExplanation())
                    .vocabulary(vocabulary)
                    .build());

            List<Option> options = List.of(
                    Option.builder()
                            .label("A")
                            .content(questionRecord.getOption1())
                            .question(question)
                            .build(),
                    Option.builder()
                            .label("B")
                            .content(questionRecord.getOption2())
                            .question(question)
                            .build(),
                    Option.builder()
                            .label("C")
                            .content(questionRecord.getOption3())
                            .question(question)
                            .build(),
                    Option.builder()
                            .label("D")
                            .content(questionRecord.getOption4())
                            .question(question)
                            .build()
            );

            optionRepository.saveAll(options);
        });
    }
}
