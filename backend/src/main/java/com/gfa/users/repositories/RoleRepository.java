package com.gfa.users.repositories;

import com.gfa.users.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  boolean existsAllByName(String roleName);
}
