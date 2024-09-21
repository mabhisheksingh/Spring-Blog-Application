package com.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


/**
 * User Entity
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user")
public class User {

   @Id
   @Field("user_id")
    private String id;
   private String name;
   private String mobileNumber;
   private String userName;
   private String email;
   private String gender;
   private List<String> followersUserNameList;
   private List<String> followingUserNameList;
   private List<BlogPost> blogPosts;
}
