package com.example.likesystem.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikeMessage {

    private Integer userId;

    private Integer articleId;

    private Boolean cancelled;
}
