package com.blog.repository;

import com.blog.model.BlogPost;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, String> {

  BlogPost findByTitle(String title);

  @Deprecated(since = "next version")
  List<BlogPost> findByAuthorUserName(String userName);

  @Query("{ '_id': ?0 }")
  Page<BlogPost> findAllWithID(String _id, Pageable pageable);

  //  @Query("{ 'userId': ?0, 'article': { $regex: ?1, $options: 'i' } }")
  //  Page<BlogPost> findByUserIdAndArticleContains(String userId, String searchTerm, Pageable
  // pageable);

  @Query("{'title': { $regex: ?1, $options: 'i' } }")
  Page<BlogPost> searchByArticleContain(String title, Pageable pageable);
}
