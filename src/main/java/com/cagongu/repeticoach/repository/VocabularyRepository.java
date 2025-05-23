package com.cagongu.repeticoach.repository;

import com.cagongu.repeticoach.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    Optional<Vocabulary> findByWord(String word);

    // Lấy từ cần ôn theo ngày
    List<Vocabulary> findByNextReviewLessThanEqual(LocalDate today);

    // Lấy từ cần ôn theo ngày và chủ đề
    @Query("SELECT v FROM Vocabulary v WHERE v.nextReview <= :today AND v.topic.name = :topic")
    List<Vocabulary> findByNextReviewLessThanEqualAndTopic(@Param("today") LocalDate today, @Param("topic") String topic);

    // Lấy từ mới
    @Query("SELECT v FROM Vocabulary v WHERE v.repetition = 0 AND v.nextReview IS NULL ORDER BY v.id ASC LIMIT :limit")
    List<Vocabulary> findNewWords(@Param("limit") int limit);

    // Lấy từ mới theo chủ đề
    @Query("SELECT v FROM Vocabulary v WHERE v.repetition = 0 AND v.nextReview IS NULL AND v.topic.name = :topic ORDER BY v.id ASC LIMIT :limit")
    List<Vocabulary> findNewWordsByTopic(@Param("limit") int limit, @Param("topic") String topic);

    // Lấy từ đã học trong x ngày vừa qua
    @Query("SELECT v FROM Vocabulary v WHERE v.lastReview IS NOT NULL AND v.lastReview >= :startDate AND v.lastReview <= :endDate")
    List<Vocabulary> findByLastReviewBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Lấy từ đã học trong x ngày vừa qua theo chủ đề
    @Query("SELECT v FROM Vocabulary v WHERE v.lastReview IS NOT NULL AND v.lastReview >= :startDate AND v.lastReview <= :endDate AND v.topic.name = :topic")
    List<Vocabulary> findByLastReviewBetweenAndTopic(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("topic") String topic);
}