package com.gfa.users.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.lang.NonNull;
import java.util.List;

@Entity
@Table(name = "permissions")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long id;

  @Column(unique = true, nullable = false)
  private String ability;

  @ManyToMany(mappedBy = "permissions")
  private List<User> users;

  @ManyToMany(mappedBy = "permissions")
  private List<Role> roles;

  @ManyToMany(mappedBy = "permissions")
  private List<Team> teams;

  public Permission() {}

  public Permission(@NonNull String ability) {
    this.ability = ability;
  }

  public Permission(Long id, String ability) {
    this.id = id;
    this.ability = ability;
  }

  @NonNull
  public String getAbility() {
    return ability;
  }

  public boolean can(String ability) {
    return ability.equals(getAbility());
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public Long getId() {
    return id;
  }
}
