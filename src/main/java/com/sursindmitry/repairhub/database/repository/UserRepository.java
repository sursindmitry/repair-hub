package com.sursindmitry.repairhub.database.repository;

import com.sursindmitry.repairhub.database.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
