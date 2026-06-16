package com.collabrium.iam.authentication.domain.model.entities;

import com.collabrium.iam.authentication.domain.model.valueobjects.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Roles name;

  public Role(Roles name) {
    this.name = name;
  }

  public String getStringName() {
    return name.name();
  }

  public static Role getDefaultRole() {
    return new Role(Roles.ROLE_USER);
  }

  public static Role toRoleFromName(String name) {
    return new Role(Roles.valueOf(name));
  }

  public static List<Role> validateRoleSet(List<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(getDefaultRole());
    }
    return roles;
  }
}