package com.collabrium.iam;

import com.collabrium.iam.authentication.application.internal.queryservices.RoleQueryServiceImpl;
import com.collabrium.iam.authentication.domain.model.entities.Role;
import com.collabrium.iam.authentication.domain.model.queries.GetAllRolesQuery;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleQueryServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleQueryServiceImpl service;

    @Test
    @DisplayName("handle(GetAllRolesQuery) - existing roles: returns list of roles")
    void handle_getAllRoles_existingRoles_returnsList() {
        // Arrange
        var role1 = mock(Role.class);
        var role2 = mock(Role.class);
        var query = new GetAllRolesQuery();

        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(role1, role2);
    }

    @Test
    @DisplayName("handle(GetAllRolesQuery) - no roles: returns empty list")
    void handle_getAllRoles_noRoles_returnsEmptyList() {
        // Arrange
        var query = new GetAllRolesQuery();

        when(roleRepository.findAll()).thenReturn(List.of());

        // Act
        var result = service.handle(query);

        // Assert
        assertThat(result).isEmpty();
    }
}