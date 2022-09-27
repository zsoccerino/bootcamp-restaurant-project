package com.gfa.users.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionTest {

  @Test
  void can_create_permissions() {
    assertEquals("create teams", new Permission("create teams").getAbility());
  }

  @Test
  void can_check_permissions() {
    assertTrue(new Permission("edit teams").can("edit teams"));
    assertFalse(new Permission("delete users").can("delete roles"));
  }
}
