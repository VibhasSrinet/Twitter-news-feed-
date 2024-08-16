package org.example.twitternewsfeed.repository;

import org.example.twitternewsfeed.models.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findAllByIdIn(List<Long> ids);
}

