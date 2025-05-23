package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.model.Vocabulary;
import com.cagongu.repeticoach.repository.VocabularyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnglishLearningService {

    private final VocabularyRepository vocabularyRepository;
    private final SpacedRepetitionService spacedRepetitionService;

    @Value("${learning.new-words-per-day:10}")
    private int newWordsPerDay;

    @Value("${learning.total-words-target:20}")
    private int totalWordsTarget;

    /**
     * Lấy danh sách từ vựng cho ngày hiện tại (kết hợp từ mới và từ cần ôn)
     *
     * @return Danh sách từ vựng đã được sắp xếp xen kẽ
     */
    public List<Vocabulary> getDailyWords(String topic) {
        LocalDate today = LocalDate.now();

        // Lấy từ cần ôn (đã học trước đó)
        List<Vocabulary> wordsToReview;
        if (topic != null && !topic.isEmpty()) {
            wordsToReview = vocabularyRepository.findByNextReviewLessThanEqualAndTopic(today, topic);
        } else {
            wordsToReview = vocabularyRepository.findByNextReviewLessThanEqual(today);
        }

        // Giới hạn số lượng từ ôn
        if (wordsToReview.size() > totalWordsTarget) {
            wordsToReview = wordsToReview.subList(0, totalWordsTarget);
        }

        // Tính số từ mới cần thêm
        int remainingSlots = totalWordsTarget - wordsToReview.size();
        List<Vocabulary> newWords = Collections.emptyList();

        if (remainingSlots > 0) {
            // Lấy từ mới (chưa có lịch ôn)
            if (topic != null) {
                newWords = vocabularyRepository.findNewWordsByTopic(topic);
            } else {
                newWords = vocabularyRepository.findNewWords(Math.min(newWordsPerDay, remainingSlots));
            }

            // Đánh dấu ngày học cho các từ mới
            newWords.forEach(word -> {
                word.setLastReview(today);
                word.setNextReview(today.plusDays(1)); // Mặc định interval 1 ngày cho từ mới
            });
            vocabularyRepository.saveAll(newWords);
        }

        // Trộn xen kẽ từ ôn và từ mới
        return interleaveLists(wordsToReview, newWords);
    }

    /**
     * Cập nhật trạng thái ôn tập cho một từ vựng
     *
     * @param vocabularyId ID của từ vựng
     * @param quality      Điểm chất lượng (0-5)
     */
    public void updateReviewStatus(Long vocabularyId, int quality) {
        if (quality < 0 || quality > 5) {
            throw new IllegalArgumentException("Quality must be between 0 and 5");
        }

        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new IllegalArgumentException("Vocabulary not found with id: " + vocabularyId));

        spacedRepetitionService.updateReview(vocabulary, quality);
    }

    /**
     * Trộn hai danh sách xen kẽ nhau
     *
     * @param list1 Danh sách 1 (ưu tiên hiển thị trước)
     * @param list2 Danh sách 2
     * @return Danh sách đã trộn
     */
    private <T> List<T> interleaveLists(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>();
        int maxSize = Math.max(list1.size(), list2.size());

        for (int i = 0; i < maxSize; i++) {
            if (i < list1.size()) {
                result.add(list1.get(i));
            }
            if (i < list2.size()) {
                result.add(list2.get(i));
            }
        }

        return result;
    }
}
