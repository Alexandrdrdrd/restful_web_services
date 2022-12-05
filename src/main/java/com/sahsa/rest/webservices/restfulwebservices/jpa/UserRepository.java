package com.sahsa.rest.webservices.restfulwebservices.jpa;

import com.sahsa.rest.webservices.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
