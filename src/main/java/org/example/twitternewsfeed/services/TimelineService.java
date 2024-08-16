package org.example.twitternewsfeed.services;

import org.example.twitternewsfeed.models.Tweet;
import org.example.twitternewsfeed.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TimelineService {

    private final TweetRepository tweetRepository;
    private final RedisTemplate<String, Long> redisTemplate;

    @Autowired
    public TimelineService(TweetRepository tweetRepository, RedisTemplate<String, Long> redisTemplate) {
        this.tweetRepository = tweetRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Tweet> getUserTimeline(String userId, int page, int size) {
        String timelineKey = "timeline:" + userId;
        Set<Long> tweetIds = redisTemplate.opsForZSet().reverseRange(timelineKey, page * size, (page + 1) * size - 1);
        if (tweetIds == null || tweetIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Tweet> tweets = tweetRepository.findAllByIdIn(tweetIds.stream().toList());
        redisTemplate.opsForZSet().removeRange(timelineKey, page * size, (page + 1) * size - 1);

        return tweets;
    }
}
