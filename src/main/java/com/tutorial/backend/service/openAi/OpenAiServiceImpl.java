package com.tutorial.backend.service.openAi;

import com.tutorial.backend.api.OpenAiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final OpenAiClient openAiClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public OpenAiServiceImpl(OpenAiClient openAiClient, @Value("${openai.api.key}") String apiKey) {
        this.openAiClient = openAiClient;
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper(); // JSON 파싱을 위한 ObjectMapper 초기화
    }

    @Override
    public String getRecommendResponseForMeFromOpenAi(String prompt) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");

        // 프롬프트 작성
        String fullPrompt = "다른 사용자가 이렇게 말했어: \"" + prompt + "\".\n" +
                "이 말을 보고 적절한 답의 말을 제안해줘.";

        request.put("messages", List.of(
                Map.of("role", "user", "content", fullPrompt)
        ));

        request.put("max_tokens", 150);

        try {
            // OpenAI API 호출
            String jsonResponse = openAiClient.getCompletion("Bearer " + apiKey, request);

            // JSON 응답을 파싱하여 "content" 필드 추출
            JsonNode rootNode = objectMapper.readTree(jsonResponse); // JSON을 파싱
            JsonNode contentNode = rootNode
                    .path("choices") // "choices" 배열
                    .get(0) // 첫 번째 choice
                    .path("message") // "message" 객체
                    .path("content"); // "content" 필드

            // content 값이 존재하면 반환, 없으면 null 반환
            return contentNode.asText(null);
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 응답을 처리하는 중 오류가 발생했습니다.";
        }
    }
}