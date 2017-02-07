package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u.id from User u")
    List<Long> getAllUserId();

    User findOneByEmail(String email);
}
