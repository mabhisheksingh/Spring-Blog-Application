package com.blog.dto;


import com.blog.utils.constants.Category;
import com.blog.utils.constants.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostDTO {
    @Null
    private String id;
    @NotNull
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 10, max = 100, message = "Title should be between 10 to 100 characters")
    private String title;
    @NotNull
    @NotEmpty(message = "Content should not be empty")
    @Size(min = 100, max = 1000, message = "Content should be between 100 to 1000 characters")
    private String content;
    @NotNull
    @NotEmpty(message = "Author user name should not be empty")
    private String authorUserName;
    @NotNull
    private List<Category> categories;
    @NotNull
    private List<Tag> tags;
    @Null
    private Instant createdTime;
    @Null
    private Instant updatedTime;
    @Null
    private Integer views;
    @Null
    private Integer likes;
}
