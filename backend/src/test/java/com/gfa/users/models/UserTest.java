package com.gfa.users.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void can_with_existing_ability_returns_true() {
    User user = new User();
    Role role = new Role("role1");
    Team team = new Team("team1");
    Permission permission1 = new Permission("ability1");
    Permission permission2 = new Permission("ability2");
    Permission permission3 = new Permission("ability3");

    user.addPermission(permission1);
    role.addPermission(permission2);
    team.addPermission(permission3);

    user.addTeam(team);
    user.addRole(role);

    assertTrue(user.can("ability1"));
    assertTrue(user.can("ability2"));
    assertTrue(user.can("ability3"));
  }

  @Test
  public void can_with_non_existing_ability_returns_false() {
    User user = new User();
    Role role = new Role("role1");
    Team team = new Team("team1");

    user.addPermission(new Permission("ability1"));
    role.addPermission(new Permission("ability2"));
    team.addPermission(new Permission("ability3"));

    team.addUser(user);
    team.addRole(role);

    // assert
    assertFalse(user.can("ability4"));
    assertFalse(user.can("ability5"));
    assertFalse(user.can("ability6"));
  }

  @Test
  void can_create_user() {

    LocalDateTime verifiedAt = LocalDateTime.now();
    LocalDateTime verificationTokenExpiresAt = LocalDateTime.now();
    LocalDateTime forgottenPasswordTokenExpiresAt = LocalDateTime.now();
    LocalDateTime createdAt = LocalDateTime.now();

    User user =
        new User(
            1L,
            "John",
            "Email@mail.com",
            "password",
            verifiedAt,
            "VerToken123",
            verificationTokenExpiresAt,
            "ForgotToken12345",
            forgottenPasswordTokenExpiresAt);
    assertEquals(1L, user.getId());
    assertEquals("Email@mail.com", user.getEmail());
    assertEquals("password", user.getPassword());
    assertEquals("John", user.getUsername());
    assertEquals(verifiedAt, user.getVerifiedAt());
    assertEquals("VerToken123", user.getVerificationToken());
    assertEquals(verificationTokenExpiresAt, user.getVerificationTokenExpiresAt());
    assertEquals("ForgotToken12345", user.getForgottenPasswordToken());
    assertEquals(forgottenPasswordTokenExpiresAt, user.getForgottenPasswordTokenExpiresAt());
    //    assertEquals(createdAt, user.getCreatedAt());
  }
}
