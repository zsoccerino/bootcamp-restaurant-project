package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleTest {

  @Test
  void can_with_existing_ability_returns_true() {

    Role role = new Role();
    role.addPermission(new Permission("ability1"));

    role.addPermission(new Permission("ability2"));
    // assert
    assertTrue(role.can("ability1"));
    assertTrue(role.can("ability2"));
  }

  @Test
  public void can_with_non_existing_ability_returns_false() {
    Role role = new Role();
    role.addPermission(new Permission("ability1"));

    role.addPermission(new Permission("ability2"));
    // assert
    assertFalse(role.can("ability3"));
    assertFalse(role.can("ability4"));
  }
}
