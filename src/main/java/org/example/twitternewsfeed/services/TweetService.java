package org.example.twitternewsfeed.services;

import org.example.twitternewsfeed.models.Tweet;
import org.example.twitternewsfeed.models.TweetEvent;
import org.example.twitternewsfeed.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RedisTemplate<String, Long> redisTemplate;


    @Autowired
    public TweetService(TweetRepository tweetRepository, KafkaTemplate<String, Object> kafkaTemplate, RedisTemplate<String, Long> redisTemplate) {
        this.tweetRepository = tweetRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.redisTemplate = redisTemplate;
    }

    public Tweet createTweet(Tweet tweet) {
        Tweet savedTweet = tweetRepository.save(tweet);
        kafkaTemplate.send("tweet-topic", getTweetEvent(savedTweet));
        String timelineKey = "timeline:" + savedTweet.getUserId();
        redisTemplate.opsForZSet().add(timelineKey,savedTweet.getId(), Instant.now().toEpochMilli());
        return savedTweet;
    }

    public TweetEvent getTweetEvent(Tweet tweet) {
        TweetEvent tweetEvent = new TweetEvent();
        tweetEvent.setTweetId(tweet.getId());
        tweetEvent.setUserId(tweet.getUserId());
        return tweetEvent;
    }
}

