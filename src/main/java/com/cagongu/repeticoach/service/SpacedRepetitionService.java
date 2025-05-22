package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.model.Vocabulary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SpacedRepetitionService {
    public void updateReview(Vocabulary vocabulary, int quality){
        float ef = vocabulary.getEf();
        int rep = vocabulary.getRepetition();
        int interval = vocabulary.getIntervalDays();

        if (quality >= 3) {
            if (rep == 0) interval = 1;
            else if (rep == 1) interval = 6;
            else interval = Math.round(interval * ef);
            rep++;
            ef = ef + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f));
            if (ef < 1.3f) ef = 1.3f;
        } else {
            rep = 0;
            interval = 1;
            ef = Math.max(1.3f, ef - 0.2f);
        }
        vocabulary.setRepetition(rep);
        vocabulary.setEf(ef);
        vocabulary.setIntervalDays(interval);
        vocabulary.setLastReview(LocalDate.now());
        vocabulary.setNextReview(LocalDate.now().plusDays(interval));
    }
}
