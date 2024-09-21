package com.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "comments")
public class Comments {
  @Id
  @Field(name = "comment_id")
  private String commentId;

  @Field(name = "content")
  private String content;

  @Field(name = "post_id")
  private int postId;

  @Field(name = "user_id")
  private int userId;
}
