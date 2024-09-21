package com.blog.repository;

import com.blog.model.BlogPost;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, String> {

    BlogPost findByTitle(String title);
    @Deprecated(since = "next version")
    List<BlogPost> findByAuthorUserName(String userName);

    // This class will contain the logic to interact with the database
    // and perform CRUD operations on the blog posts table
    // (Assuming you are using Java and a database library like JPA or Hibernate)

}
