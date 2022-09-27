package com.gfa.users.repositories;

import com.gfa.users.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
  boolean existsAllByName(String teamName);
}
