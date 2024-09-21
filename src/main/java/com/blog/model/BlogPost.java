package com.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "blog_post")
public class BlogPost {
    @Id
    @Field(name = "post_id")
    private String id;
    private String title;
    private String content;
    @Field(name = "user_id")
    private String authorId;
    private String category;
    private String tags;
    private Instant createTime;
    private Instant updateTime;
    private Integer views;
    private Integer likes;
    @Field(name = "comments_id")
    private List<String> commentsId;
}
