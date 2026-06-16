package com.collabrium.iam.authentication.domain.model.aggregates;

import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.domain.model.valueobjects.LeaderId;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import com.collabrium.iam.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AttributeOverrides({
    @AttributeOverride(name = "leaderId.value", column = @Column(name = "leader_id")),
    @AttributeOverride(name = "memberId.value", column = @Column(name = "member_id"))
})
public class User extends AuditableAbstractAggregateRoot<User> {

  @Column(unique = true, nullable = false, length = 50)
  private String username;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String surname;

  @Column(nullable = false)
  private String imgUrl;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false, length = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @Nullable
  @Embedded
  private LeaderId leaderId;

  @Nullable
  @Embedded
  private MemberId memberId;

  public User() {
    this.roles = new HashSet<>();
  }

  public User(String username,
              String name,
              String surname,
              String imgUrl,
              String email,
              String password) {
    this();
    this.username = username;
    this.name = name;
    this.surname = surname;
    this.imgUrl = imgUrl;
    this.email = email;
    this.password = password;
  }

  public User(String username,
              String name,
              String surname,
              String imgUrl,
              String email,
              String password,
              List<Role> roles) {
    this(username, name, surname, imgUrl, email, password);
    addRoles(roles);
  }

  public User addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  public void addRoles(List<Role> roles) {
    var validatedRoleSet = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoleSet);
  }
}