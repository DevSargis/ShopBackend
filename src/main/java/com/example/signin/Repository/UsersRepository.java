package com.example.signin.Repository;

import com.example.signin.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

}
