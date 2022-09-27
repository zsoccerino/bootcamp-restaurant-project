package com.gfa.users.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "email_verifications")
public class EmailVerification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne private User user;

  @NonNull
  @Column(unique = true)
  private String token;

  @NonNull
  @Column(name = "expires_at")
  private LocalDateTime expiresAt;

  public EmailVerification() {}

  public EmailVerification(User user, @NonNull String token, @NonNull LocalDateTime expiresAt) {
    this.user = user;
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @NonNull
  public String getToken() {
    return token;
  }

  public void setToken(@NonNull String token) {
    this.token = token;
  }

  @NonNull
  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(@NonNull LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
}
