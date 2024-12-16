package com.example.likesystem.service.impl;


import com.example.likesystem.kafka.LikeMessage;
import com.example.likesystem.service.LikeService;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class LiseServiceImpl implements LikeService {

    @Autowired
    private KafkaTemplate<Integer, LikeMessage> kafkaTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LoadingCache<Integer, AtomicInteger> cache;


    @Override
    public void giveLike(Integer userId,Integer articleId) {
        kafkaTemplate.send("topic1",userId,new LikeMessage(userId,articleId,false));
        cache.get(articleId).incrementAndGet();
        if(Math.random()*10<1){
            updateLikeCount(articleId,cache.get(articleId).getAndSet(0));
        }
    }

    @Override
    public void cancelLike(Integer userId,Integer articleId) {
        kafkaTemplate.send("topic1",userId,new LikeMessage(userId,articleId,true));
        cache.get(articleId).decrementAndGet();
        if(Math.random()*10<1){
            updateLikeCount(articleId,cache.get(articleId).getAndSet(0));
        }
    }

    @Override
    public void updateLikeCount(Integer articleId, Integer count) {
        String sql = "insert into article_like (article_id, like_count) values (?,?) on duplicate key update like_count = like_count +  "+count;
        jdbcTemplate.update(sql, articleId,count);
    }

    @Override
    public void updateLike(Integer userId, Integer articleId, Boolean cancelled) {
        Integer value = cancelled ? 1 : 0;
        String sql = "insert into likes (user_id,article_id,cancelled) values (?,?,?) on duplicate key update cancelled="+value;
        jdbcTemplate.update(sql, userId, articleId, value);
    }

    @Override
    public Integer queryLikeCount(Integer articleId) {
        String sql = "select like_count from article_like where article_id=?";
        return jdbcTemplate.query(sql, new Object[]{articleId}, (rs) -> {
            if(!rs.next()){
                return 0;
            }
            return rs.getInt("like_count");
        });
    }

    @Override
    public Boolean queryLikeExist(Integer userId, Integer articleId)  {
        String sql = "select cancelled from likes where user_id=? and article_id=?";
        return jdbcTemplate.query(sql, new Object[]{userId, articleId}, (rs) -> {
            if(!rs.next()){
                return false;
            }
            Integer cancelled = rs.getInt("cancelled");
            return cancelled != null && cancelled == 0;
        });
    }
}
