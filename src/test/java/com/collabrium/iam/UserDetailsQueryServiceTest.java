package com.collabrium.iam;

import com.collabrium.iam.authentication.application.internal.outboundservices.ports.GroupsQueryPort;
import com.collabrium.iam.authentication.application.internal.outboundservices.ports.TasksQueryPort;
import com.collabrium.iam.authentication.application.internal.queryservices.UserDetailsQueryService;
import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.domain.model.queries.GetAllUsersQuery;
import com.collabrium.iam.authentication.domain.model.queries.GetUserByIdQuery;
import com.collabrium.iam.authentication.domain.model.valueobjects.LeaderId;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import com.collabrium.iam.authentication.domain.model.valueobjects.Roles;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import com.collabrium.iam.shared.infrastructure.clients.groups.resources.LeaderResource;
import com.collabrium.iam.shared.infrastructure.clients.tasks.resources.MemberResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsQueryServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    GroupsQueryPort groupsQueryPort;

    @Mock
    TasksQueryPort tasksQueryPort;

    @InjectMocks
    UserDetailsQueryService service;

    // --- GetUserByIdQuery ---

    @Test
    @DisplayName("handle(GetUserByIdQuery) - user without leader/member ids: returns DTO with null resources")
    void handle_getUserById_userWithoutIds_returnsDTO() {
        // Arrange
        var user = new User("user1", "John", "Doe", "img.png", "john@test.com", "pass");
        user.addRole(new Role(Roles.ROLE_USER));

        var query = new GetUserByIdQuery(1L);

        when(userRepository.findByIdWithRoles(1L)).thenReturn(Optional.of(user));

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isPresent();
        var dto = result.get();
        assertThat(dto.username()).isEqualTo("user1");
        assertThat(dto.leaderResource()).isNull();
        assertThat(dto.memberResource()).isNull();
    }

    @Test
    @DisplayName("handle(GetUserByIdQuery) - user with leader and member ids: calls ports and returns DTO")
    void handle_getUserById_userWithIds_callsPortsAndReturnsDTO() {
        // Arrange
        var user = new User("user2", "Jane", "Doe", "img.png", "jane@test.com", "pass");
        user.addRole(new Role(Roles.ROLE_USER));
        user.setLeaderId(new LeaderId(10L));
        user.setMemberId(new MemberId(20L));

        var query = new GetUserByIdQuery(2L);
        var mockLeader = mock(LeaderResource.class);
        var mockMember = mock(MemberResource.class);

        when(userRepository.findByIdWithRoles(2L)).thenReturn(Optional.of(user));
        when(groupsQueryPort.getLeaderById(10L)).thenReturn(mockLeader);
        when(tasksQueryPort.getMemberById(20L)).thenReturn(mockMember);

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isPresent();
        var dto = result.get();
        assertThat(dto.leaderResource()).isSameAs(mockLeader);
        assertThat(dto.memberResource()).isSameAs(mockMember);
    }

    @Test
    @DisplayName("handle(GetUserByIdQuery) - user not found: returns empty")
    void handle_getUserById_userNotFound_returnsEmpty() {
        // Arrange
        var query = new GetUserByIdQuery(99L);

        when(userRepository.findByIdWithRoles(99L)).thenReturn(Optional.empty());

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isEmpty();
    }
}