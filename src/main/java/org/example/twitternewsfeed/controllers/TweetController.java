package org.example.twitternewsfeed.controllers;

import org.example.twitternewsfeed.models.Tweet;
import org.example.twitternewsfeed.services.TimelineService;
import org.example.twitternewsfeed.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final TimelineService timelineService;

    @Autowired
    public TweetController(TweetService tweetService, TimelineService timelineService) {
        this.tweetService = tweetService;
        this.timelineService = timelineService;
    }

    @PostMapping
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.createTweet(tweet);
    }

    @GetMapping("/timeline")
    public List<Tweet> getUserTimeline(@RequestParam String userId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return timelineService.getUserTimeline(userId, page, size);
    }
}

