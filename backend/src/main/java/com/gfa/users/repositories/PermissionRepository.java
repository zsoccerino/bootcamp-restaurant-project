package com.gfa.users.repositories;

import com.gfa.users.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

  boolean existsAllByAbility(String ability);
}
