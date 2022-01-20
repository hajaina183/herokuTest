package com.springboot.app;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends MongoRepository<AdminModel,String> {
	@Query("{username: ?0, password: ?1}")                            // SQL Equivalent : SELECT * FROM BOOK where author = ? and cost=?
    //@Query("{$and :[{author: ?0},{cost: ?1}] }")
    List<AdminModel> getAdminModelLogged(String username, String password);
}
