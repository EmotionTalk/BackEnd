package com.tutorial.backend.service.openAi;

import com.tutorial.backend.api.OpenAiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private final OpenAiClient openAiClient;
    private final String apiKey;

    public OpenAiServiceImpl(OpenAiClient openAiClient, @Value("${openai.api.key}") String apiKey) {
        this.openAiClient = openAiClient;
        this.apiKey = apiKey;
    }

    public String getRecommendResponseForMeFromOpenAi(String prompt) {
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");

        // 더 구체적인 프롬프트로 요청 구성
        String fullPrompt = "사용자가 이렇게 말했어: \"" + prompt + "\".\n" +
                "이 말을 들은 상대방이 어떻게 생각할지 예측하고, " +
                "더 나은 표현을 두줄로 제안해줘.";

        request.put("messages", List.of(
                Map.of("role", "user", "content", fullPrompt)
        ));

        request.put("max_tokens", 150);

        return openAiClient.getCompletion("Bearer " + apiKey, request);
    }
}
