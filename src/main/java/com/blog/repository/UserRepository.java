package com.blog.repository;

import com.blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  User findByUserName(String userName);

  Long deleteByUserName(String userName);
}
