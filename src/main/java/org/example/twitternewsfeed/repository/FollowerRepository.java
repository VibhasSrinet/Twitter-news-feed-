package org.example.twitternewsfeed.repository;

import org.example.twitternewsfeed.models.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByFollowedId(Long followedId);
}

