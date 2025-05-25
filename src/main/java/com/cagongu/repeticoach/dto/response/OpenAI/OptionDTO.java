package com.cagongu.repeticoach.dto.response.OpenAI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO {
    private String label;
    private String context;
}
