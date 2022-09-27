package com.gfa.users.models;

import com.gfa.common.dtos.NewUserRequestDto;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "verified_at")
  private LocalDateTime verifiedAt = null;

  @Column(unique = true, nullable = false, name = "verification_token")
  private String verificationToken;

  @Column(nullable = false, name = "verification_token_expires_at")
  private LocalDateTime verificationTokenExpiresAt;

  @Column(unique = true, nullable = true, name = "forgotten_password_token")
  private String forgottenPasswordToken;

  @Column(nullable = true, name = "forgotten_password_token_expires_at")
  private LocalDateTime forgottenPasswordTokenExpiresAt;

  @Column(nullable = false, name = "created_at")
  private LocalDateTime createdAt;

  @ManyToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "users"),
      inverseJoinColumns = @JoinColumn(name = "permission"))
  private Set<Permission> permissions;

  @ManyToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "users"),
      inverseJoinColumns = @JoinColumn(name = "role"))
  private Set<Role> roles;

  @ManyToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "users"),
      inverseJoinColumns = @JoinColumn(name = "team"))
  private Set<Team> teams;

  private boolean mfa;

  private String secret;

  public User(
      Long id,
      String username,
      String email,
      String password,
      LocalDateTime verifiedAt,
      String verificationToken,
      LocalDateTime verificationTokenExpiresAt,
      String forgottenPasswordToken,
      LocalDateTime forgottenPasswordTokenExpiresAt) {
    this();
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.verifiedAt = verifiedAt;
    this.verificationToken = verificationToken;
    this.verificationTokenExpiresAt = verificationTokenExpiresAt;
    this.forgottenPasswordToken = forgottenPasswordToken;
    this.forgottenPasswordTokenExpiresAt = forgottenPasswordTokenExpiresAt;
  }

  public User(NewUserRequestDto dto, String token, LocalDateTime tokenExpiresAt) {
    this();
    this.username = dto.username;
    this.email = dto.email;
    this.password = dto.password;
    this.verifiedAt = null;
    this.verificationToken = token;
    this.verificationTokenExpiresAt = tokenExpiresAt;
    this.forgottenPasswordToken = null;
  }

  public User() {
    initializeDefaults();
  }

  public User(String passwordHash) {
    this.password = passwordHash;
  }

  private void initializeDefaults() {
    verifiedAt = null;
    verificationTokenExpiresAt = LocalDateTime.now();
    forgottenPasswordTokenExpiresAt = LocalDateTime.now();
    createdAt = LocalDateTime.now();
    permissions = new HashSet<>();
    roles = new HashSet<>();
    teams = new HashSet<>();

  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getVerifiedAt() {
    return verifiedAt;
  }

  public void setVerifiedAt(LocalDateTime localDateTime) {
    this.verifiedAt = localDateTime;
    this.verificationToken = null;
  }

  public String getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(String verificationToken) {
    this.verificationToken = verificationToken;
    this.verifiedAt = null;
  }

  public LocalDateTime getVerificationTokenExpiresAt() {
    return verificationTokenExpiresAt;
  }

  public void setVerificationTokenExpiresAt(LocalDateTime verificationTokenExpiresAt) {
    this.verificationTokenExpiresAt = verificationTokenExpiresAt;
  }

  public String getForgottenPasswordToken() {
    return forgottenPasswordToken;
  }

  public void setForgottenPasswordToken(String forgottenPasswordToken) {
    this.forgottenPasswordToken = forgottenPasswordToken;
  }

  public LocalDateTime getForgottenPasswordTokenExpiresAt() {
    return forgottenPasswordTokenExpiresAt;
  }

  public void setForgottenPasswordTokenExpiresAt(LocalDateTime forgottenPasswordTokenExpiresAt) {
    this.forgottenPasswordTokenExpiresAt = forgottenPasswordTokenExpiresAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public boolean isMfa() {
    return mfa;
  }

  public void setMfa(boolean mfa) {
    this.mfa = mfa;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public boolean addPermission(Permission permission) {
    return permissions.add(permission);
  }

  public boolean addTeam(Team team) {
    return teams.add(team);
  }

  public boolean addRole(Role role) {
    return roles.add(role);
  }

  public boolean can(String ability) {
    for (Permission permission : permissions) {
      if (permission.can(ability)) {
        return true;
      }
    }
    for (Role role : roles) {
      if (role.can(ability)) {
        return true;
      }
    }
    for (Team team : teams) {
      if (team.can(ability)) {
        return true;
      }
    }
    return false;
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }
}
