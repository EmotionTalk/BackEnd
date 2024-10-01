package com.tutorial.backend.controller;

import com.tutorial.backend.controller.dto.ChatMessageDto;
import com.tutorial.backend.controller.dto.ResultDto;
import com.tutorial.backend.entity.Message;
import com.tutorial.backend.entity.type.MessageType;
import com.tutorial.backend.service.message.MessageService;
import com.tutorial.backend.service.openAi.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/OpenAI/")
@RequiredArgsConstructor
@Slf4j
public class OpenAiController {

    private final OpenAiService openAiService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate; // WebSocket 방송을 위한 템플릿

    @PostMapping("ask")
    public ResultDto<String> askOpenAi(@RequestBody Map<String, String> requestBody) {

        // 메시지를 데이터베이스에서 조회
        Optional<Message> foundMessage = messageService.getMessageById(requestBody.get("id"));

        try {
            if (foundMessage.isPresent()) {
                Message message = foundMessage.get();

                // AI 추천 응답이 없다면 AI로부터 응답을 받아와 저장
                if (message.getAiSuggestion() == null || message.getAiSuggestion().isEmpty()) {
                    // AI 응답 받기
                    String result = openAiService.getRecommendResponseForMeFromOpenAi(message.getContent());

                    // AI 응답을 기존 메시지에 추가
                    message.setAiSuggestion(result);
                    messageService.saveMessage(message); // 메시지 업데이트

                    // WebSocket으로 수정된 메시지 방송
                    ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                            .id(message.getId()) // 기존 메시지 ID 유지
                            .message(message.getContent())  // 메시지 내용
                            .aiSuggestion(result)           // AI 응답 추가된 필드
                            .messageType(MessageType.valueOf(message.getType()))  // 메시지 타입
                            .senderId(message.getMemberId()) // 발신자
                            .emotionNum(message.getEmotionNum())
                            .isDeleted(message.getIsDeleted())
                            .sendTime(message.getSendTime())
                            .chatRoomId(message.getChatRoomId()) // 채팅방 ID
                            .build();

                    // 구독자들에게 수정된 메시지 방송
                    messagingTemplate.convertAndSend("/sub/chatroom/" + message.getChatRoomId(), chatMessageDto);

                    log.info("AI 응답을 메시지에 저장 및 방송 완료");
                    return ResultDto.res(HttpStatus.ACCEPTED, "AI가 이에 따른 답변을 들고왔어요!", result);
                } else {
                    return ResultDto.res(HttpStatus.BAD_REQUEST, "AI 응답이 이미 존재합니다");
                }
            } else {
                return ResultDto.res(HttpStatus.NOT_FOUND, "메시지를 찾을 수 없습니다");
            }
        } catch (NullPointerException e) {
            return ResultDto.res(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 생겼어요 ㅠㅠ");
        } catch (Exception e) {
            return ResultDto.res(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다");
        }
    }

    @PostMapping("askForMe")
    public ResultDto<String> askOpenAiForMe(@RequestBody Map<String, String> requestBody){
        String content = requestBody.get("content");

        try{
            String result = openAiService.getRecommendForMe(content);

            return ResultDto.res(HttpStatus.ACCEPTED,"적절한 답변을 받아왔어요!",result);

        }catch (Exception e){
            return ResultDto.res(HttpStatus.INTERNAL_SERVER_ERROR,"서버 오류가 생겼어요 ㅠㅠ");
        }

    }



}