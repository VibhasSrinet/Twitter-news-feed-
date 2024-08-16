package org.example.twitternewsfeed.services;

import org.example.twitternewsfeed.models.Follower;
import org.example.twitternewsfeed.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final FollowerRepository followerRepository;

    @Autowired
    public UserService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    public List<Long> getFollowers(Long userId) {
        List<Follower> followers = followerRepository.findByFollowedId(userId);
        return followers.stream()
                .map(Follower::getFollowerId)
                .collect(Collectors.toList());
    }
}

