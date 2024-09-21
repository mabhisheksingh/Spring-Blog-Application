package com.blog.model;

import com.blog.utils.constants.Category;
import com.blog.utils.constants.Tag;

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
    private String id;
    private String title;
    private String content;
    @Field(name = "author_id")
    private String authorUserName;
    private List<Category> category;
    private List<Tag> tags;
    private Instant createdTime;
    private Instant updatedTime;
    private Integer views;
    private Integer likes;
    @Field(name = "comments_id")
    private List<String> commentsId;
}
