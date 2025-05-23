package com.cagongu.repeticoach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewWordRequest {
    private Long vocabularyId;
    private int quality;
}
