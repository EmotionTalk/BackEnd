package com.tutorial.backend.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;

@FeignClient(name = "openai", url = "https://api.openai.com/v1")
public interface OpenAiClient {

    @PostMapping("/chat/completions")
    String getCompletion(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, Object> request);
}
