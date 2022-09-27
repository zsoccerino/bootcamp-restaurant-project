package com.gfa.users.repositories;

import com.gfa.users.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findUserByForgottenPasswordToken(String token);

  Optional<User> findUserByEmail(String email);
}
