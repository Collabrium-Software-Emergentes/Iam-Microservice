package com.collabrium.iam;

import com.collabrium.iam.authentication.application.internal.queryservices.UserQueryServiceImpl;
import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.queries.GetUserByMemberIdQuery;
import com.collabrium.iam.authentication.domain.model.queries.GetUserOnlyByIdQuery;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserQueryServiceImpl service;

    // --- GetUserOnlyByIdQuery ---

    @Test
    @DisplayName("handle(GetUserOnlyByIdQuery) - existing user: returns user")
    void handle_getUserOnlyById_existingUser_returnsUser() {
        // Arrange
        var user = mock(User.class);
        var query = new GetUserOnlyByIdQuery(2L);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).containsSame(user);
    }

    @Test
    @DisplayName("handle(GetUserOnlyByIdQuery) - user not found: returns empty")
    void handle_getUserOnlyById_userNotFound_returnsEmpty() {
        // Arrange
        var query = new GetUserOnlyByIdQuery(2L);

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isEmpty();
    }

    // --- GetUserByMemberIdQuery ---

    @Test
    @DisplayName("handle(GetUserByMemberIdQuery) - existing member: returns user")
    void handle_getUserByMemberId_existingMember_returnsUser() {
        // Arrange
        var user = mock(User.class);
        var query = new GetUserByMemberIdQuery(500L);

        when(userRepository.findByMemberId(any(MemberId.class))).thenReturn(Optional.of(user));

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).containsSame(user);
    }

    @Test
    @DisplayName("handle(GetUserByMemberIdQuery) - member not found: returns empty")
    void handle_getUserByMemberId_memberNotFound_returnsEmpty() {
        // Arrange
        var query = new GetUserByMemberIdQuery(500L);

        when(userRepository.findByMemberId(any(MemberId.class))).thenReturn(Optional.empty());

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isEmpty();
    }
}