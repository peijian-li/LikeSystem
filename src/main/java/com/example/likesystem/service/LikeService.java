package com.example.likesystem.service;

public interface LikeService {

    void giveLike(Integer userId,Integer articleId) ;

    void cancelLike(Integer userId,Integer articleId) ;

    void updateLikeCount(Integer articleId,Integer count);

    void updateLike(Integer userId, Integer articleId, Boolean cancelled);

    Integer queryLikeCount(Integer articleId);

    Boolean queryLikeExist(Integer userId,Integer articleId) ;

}
