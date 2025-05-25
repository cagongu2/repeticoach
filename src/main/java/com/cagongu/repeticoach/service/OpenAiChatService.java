package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.dto.request.OpenAI.OpenAIMessageRequest;
import com.cagongu.repeticoach.dto.request.OpenAI.OpenAIChatRequest;
import com.cagongu.repeticoach.dto.response.OpenAI.OpenAIChatResponse;
import com.cagongu.repeticoach.dto.response.OpenAI.QuizResponse;
import com.cagongu.repeticoach.repository.QuestionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OpenAiChatService {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public OpenAiChatService(
            QuestionRepository questionRepository,
            RestTemplate restTemplate,
            @Value("${openai.api.chat-url}") String apiUrl,
            @Value("${spring.ai.openai.api-key}") String apiKey,
            ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    public QuizResponse generateQuestionQuiz(List<String> words) throws JsonProcessingException {
        String prompt = String.format(
                """
                        Tạo một tệp JSON chứa các câu hỏi trắc nghiệm dựa trên danh sách từ vựng: %s. Mỗi từ vựng sẽ được sử dụng để tạo một câu hỏi trắc nghiệm với các yêu cầu sau:
                        - **Loại câu hỏi**: Đa dạng, bao gồm:
                          1. Chọn nghĩa đúng (ví dụ: A "[từ]" is:).
                          2. Điền từ vào câu (ví dụ: Which word fits: "[câu có chỗ trống]"?).
                          3. Chọn từ đồng nghĩa/trái nghĩa (ví dụ: Which word is a synonym for "[từ]"?).
                          4. Đoán nghĩa qua ngữ cảnh (ví dụ: Based on this context: "[câu ngữ cảnh]", what does "[từ]" mean?).
                        - **Định dạng**:
                          - Mỗi câu hỏi có 4 đáp án (a, b, c, d), chỉ một đáp án đúng.
                          - Đáp án đúng phản ánh chính xác nghĩa hoặc cách dùng của từ.
                          - Đáp án sai phải hợp lý, thuộc cùng ngữ cảnh hoạt động hàng ngày, và có tính thách thức (tránh các đáp án quá rõ ràng sai).
                          - Bao gồm giải thích ngắn gọn bằng tiếng Việt (tối đa 20 từ).
                        - **Cấu trúc JSON**:
                        ```json
                        {
                          "questions": [
                            {
                              "word": "[từ vựng]",
                              "type": "[meaning/fill-in/synonym-antonym/context]",
                              "question": "[câu hỏi]",
                              "options": [
                                {
                                    "label": "a",
                                    "context": "[đáp án]"
                                }
                                {
                                    "label": "b",
                                    "context": "[đáp án]"
                                }{
                                    "label": "c",
                                    "context": "[đáp án]"
                                }{
                                    "label": "d",
                                    "context": "[đáp án]"
                                }
                              ],
                              "correct_answer": "[a/b/c/d]",
                              "explanation": "[giải thích]"
                            },
                            ...
                          ]
                        }
                        ```
                        - **Yêu cầu bổ sung**:
                          - Đảm bảo câu hỏi phù hợp với ngữ cảnh hoạt động hàng ngày.
                          - Phân bố đều các loại câu hỏi nếu có thể.
                          - Giải thích ngắn gọn, đúng trọng tâm, không quá 20 từ.
                        Trả về chỉ tệp JSON đúng định dạng trên.""", words);

        OpenAIMessageRequest messageRequest = OpenAIMessageRequest.builder()
                .role("user")
                .content(prompt)
                .build();



        OpenAIChatRequest request = OpenAIChatRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Collections.singletonList(messageRequest))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<OpenAIChatRequest> entity = new HttpEntity<>(request, headers);
        String response = restTemplate.postForObject(apiUrl, entity, String.class);

        OpenAIChatResponse openAIResponse = objectMapper.readValue(response, OpenAIChatResponse.class);
        String jsonContent = openAIResponse.getChoices().get(0).getMessage().getContent();

        return objectMapper.readValue(jsonContent, QuizResponse.class);
    }
}
