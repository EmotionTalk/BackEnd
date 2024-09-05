package com.tutorial.backend.controller;

import com.tutorial.backend.service.openAi.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/OpenAI")
@RequiredArgsConstructor
@Slf4j
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/ask")
    public String askOpenAi(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        String result = openAiService.getRecommendResponseForMeFromOpenAi(prompt);
        log.info(result);
        return result;
    }
}
