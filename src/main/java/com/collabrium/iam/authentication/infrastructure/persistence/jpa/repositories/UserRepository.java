package com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * This method is responsible for checking if the user exists by username.
   * @param username The username.
   * @return True if the user exists, false otherwise.
   */
  boolean existsByUsername(String username);

  /**
   * This method is responsible for checking if the user exists by email.
   * @param email The email.
   * @return True if the user exists, false otherwise.
   */
  boolean existsByEmail(String email);

  /**
   * This method is responsible for finding the user by member ID.
   * @param memberId The member ID.
   * @return The user object.
   */
  Optional<User> findByMemberId(MemberId memberId);

  @Query("""
      SELECT u
      FROM User u
      LEFT JOIN FETCH u.roles
      WHERE u.username = :username
      """)
  Optional<User> findByUsernameWithRoles(@Param("username") String username);

  @Query("""
    SELECT DISTINCT u
    FROM User u
    LEFT JOIN FETCH u.roles
    """)
  List<User> findAllWithRoles();

  @Query("""
    SELECT u
    FROM User u
    LEFT JOIN FETCH u.roles
    WHERE u.id = :id
    """)
  Optional<User> findByIdWithRoles(Long id);
}
