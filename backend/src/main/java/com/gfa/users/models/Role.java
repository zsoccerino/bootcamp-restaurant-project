package com.gfa.users.models;

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
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  @ManyToMany(mappedBy = "roles")
  private Set<Team> teams;

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public void addPermission(Permission permission) {
    this.permissions.add(permission);
  }

  public void removePermission(Permission permission) {
    this.permissions.remove(permission);
  }

  public Role() {
    this.permissions = new HashSet<>();
  }

  public Role(String name) {
    this();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }

  public boolean can(String ability) {
    for (Permission permission : permissions) {
      if (permission.can(ability)) {
        return true;
      }
    }
    return false;
  }

  public boolean can(Permission permission) {
    return can(permission.getAbility());
  }
}
