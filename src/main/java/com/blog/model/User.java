package com.blog.model;

import com.blog.utils.constants.Gender;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** User Entity */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user")
public class User {

  @Id private String id;
  private String firstName;
  private String lastName;
  private String mobileNumber;

  @Indexed(unique = true)
  private String userName;

  @Indexed(unique = true)
  private String email;

  private Gender gender;
  private List<String> followersUserNameList;
  private List<String> followingUserNameList;
  private List<BlogPost> blogPosts;
}
