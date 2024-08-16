package org.example.twitternewsfeed.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Tweet extends BaseClass {
    private Long id;
    private String content;
    private Long userId;
}

