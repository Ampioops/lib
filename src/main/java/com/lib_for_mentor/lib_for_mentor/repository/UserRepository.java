package com.lib_for_mentor.lib_for_mentor.repository;

import com.lib_for_mentor.lib_for_mentor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
