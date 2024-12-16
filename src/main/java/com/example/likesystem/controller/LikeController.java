package com.example.likesystem.controller;


import com.example.likesystem.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.likesystem.response.Result;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/give-like")
    public Result giveLike(Integer userId,Integer articleId){
        try {
            likeService.giveLike(userId,articleId);
            return Result.success("点赞成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/cancel-like")
    public Result cancelLike(Integer userId,Integer articleId){
        try {
            likeService.cancelLike(userId,articleId);
            return Result.success("取消点赞成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/query-like-count")
    public Result queryLikeCount(Integer articleId){
        try {
            Integer count = likeService.queryLikeCount(articleId);
            return Result.success("查询点赞数成功",count);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/query-like-exist")
    public Result queryLikeExist(Integer userId,Integer articleId){
        try {
            Boolean exist = likeService.queryLikeExist(userId, articleId);
            return Result.success("查询点赞成功",exist);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }


}
