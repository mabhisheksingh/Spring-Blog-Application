package com.blog.repository;

import com.blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

    User findByUserName(String userName);
}
