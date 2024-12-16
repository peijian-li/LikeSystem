package com.example.likesystem.kafka;

import com.example.likesystem.service.LikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaConsumer {
    @Autowired
    private LikeService likeService;

    @KafkaListener(topics = {"topic1"},groupId = "group1")
    public void listen(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LikeMessage likeMessage = objectMapper.readValue(message, LikeMessage.class);
        likeService.updateLike(likeMessage.getUserId(),likeMessage.getArticleId(),likeMessage.getCancelled());
    }

}
