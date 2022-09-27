package com.gfa.users.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
@Table(name = "teams")
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      joinColumns = @JoinColumn(name = "teams"),
      inverseJoinColumns = @JoinColumn(name = "role"))
  private Set<Role> roles;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      joinColumns = @JoinColumn(name = "teams"),
      inverseJoinColumns = @JoinColumn(name = "permission"))
  private Set<Permission> permissions;

  @ManyToMany(mappedBy = "teams")
  private Set<User> users;

  public Team() {
    this.permissions = new HashSet<>();
    this.users = new HashSet<>();
    this.roles = new HashSet<>();
  }

  public Team(String name) {
    this();
    this.name = name;
  }

  public Team(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean addPermission(Permission permission) {
    return permissions.add(permission);
  }

  public boolean addRole(Role role) {
    return roles.add(role);
  }

  public boolean addUser(User user) {
    return users.add(user);
  }

  public boolean removeRole(Role role) {
    return roles.remove(role);
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
    return false;
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }

  public void removeUser(User user) {
    this.users.remove(user);
  }

  public void removePermission(Permission permission) {
    this.permissions.remove(permission);
  }
}
