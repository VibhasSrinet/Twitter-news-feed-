package org.example.twitternewsfeed.services;
import org.example.twitternewsfeed.models.TweetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TweetEventListener {

    private final RedisTemplate<String, Long> redisTemplate;
    private final UserService userService;

    @Autowired
    public TweetEventListener(RedisTemplate<String, Long> redisTemplate, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @KafkaListener(topics = "tweet-topic", groupId = "tweet-group")
    public void handleTweetEvent(TweetEvent tweetEvent) {
        Long tweetId = tweetEvent.getTweetId();
        Long tweetUserId = tweetEvent.getUserId();
        long timestamp = Instant.now().toEpochMilli();
        System.out.println("Received tweet event: " + tweetId);


        // Fetch the list of followers
        List<Long> followers = userService.getFollowers(tweetUserId);

        // Add the tweet to the Redis timelines of all followers
        for (Long followerId : followers) {
            String followerTimelineKey = "timeline:" + followerId;
            redisTemplate.opsForZSet().add(followerTimelineKey,tweetId, timestamp);
        }
    }
}
