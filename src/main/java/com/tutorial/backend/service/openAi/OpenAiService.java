package com.tutorial.backend.service.openAi;

public interface OpenAiService {
    public String getRecommendResponseForMeFromOpenAi(String prompt);

    public String getRecommendForMe(String prompt);
}
