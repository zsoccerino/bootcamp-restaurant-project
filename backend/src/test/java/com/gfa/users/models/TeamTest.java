package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TeamTest {

  @Test
  void can_with_existing_ability_returns_true() {

    Role role = new Role();
    Team team = new Team();

    role.addPermission(new Permission("ability1"));
    team.addPermission(new Permission("ability2"));

    team.addRole(role);
    // assert
    assertTrue(team.can("ability1"));
    assertTrue(team.can("ability2"));
  }

  @Test
  public void can_with_non_existing_ability_returns_false() {
    Role role = new Role();
    Team team = new Team();

    role.addPermission(new Permission("ability1"));
    team.addPermission(new Permission("ability2"));

    team.addRole(role);
    // assert
    assertFalse(team.can("ability3"));
    assertFalse(team.can("ability4"));
  }
}
