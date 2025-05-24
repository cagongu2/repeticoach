package com.cagongu.repeticoach.controller;

import com.cagongu.repeticoach.dto.request.UpdateReviewWordRequest;
import com.cagongu.repeticoach.dto.response.VocabularyDTO;
import com.cagongu.repeticoach.model.Vocabulary;
import com.cagongu.repeticoach.service.EnglishLearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningController {

    private final EnglishLearningService learningService;

    /**
     * Lấy danh sách từ vựng cần học/ôn trong ngày
     *
     * @return Danh sách từ vựng đã được sắp xếp
     */
    @GetMapping("/daily-words")
    public List<VocabularyDTO> getDailyWords(@RequestParam(value = "topic", required = false) String topic) {
        return learningService.getDailyWords(topic);
    }

    /**
     * Cập nhật kết quả ôn tập một từ vựng
     *
     * @param request Dữ liệu cập nhật (id từ vựng và điểm chất lượng)
     * @return Thông tin từ vựng đã được cập nhật
     */
    @PostMapping("/update-review")
    public void updateReviewStatus(@RequestBody UpdateReviewWordRequest request) {
        learningService.updateReviewStatus(
                request.getVocabularyId(),
                request.getQuality()
        );
    }

    /**
     * Lấy danh sách từ vựng đã học trong x ngày vừa qua, có thể lọc theo chủ đề.
     * @param days Số ngày nhìn lại (bắt buộc).
     * @param topic Tên chủ đề (tùy chọn).
     * @return Danh sách từ vựng đã học.
     */
    @GetMapping("/learned-words")
    public List<VocabularyDTO> getLearnedWordsInLastXDays(
            @RequestParam(value = "days") int days,
            @RequestParam(value = "topic", required = false) String topic) {
        return learningService.getLearnedWordsInLastXDays(days, topic);
    }


}
