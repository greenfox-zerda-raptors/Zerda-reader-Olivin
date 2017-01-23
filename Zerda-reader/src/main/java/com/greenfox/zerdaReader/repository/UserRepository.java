package com.greenfox.zerdaReader.repository;

import com.greenfox.zerdaReader.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zoloe on 2017. 01. 23..
 */
public interface UserRepository extends CrudRepository<User, Long>{
}
