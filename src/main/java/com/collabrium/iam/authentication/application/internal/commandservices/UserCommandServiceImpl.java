package com.collabrium.iam.authentication.application.internal.commandservices;

import com.collabrium.iam.authentication.application.internal.outboundservices.hashing.HashingService;
import com.collabrium.iam.authentication.application.internal.outboundservices.messaging.IamEventPublisher;
import com.collabrium.iam.authentication.application.internal.outboundservices.tokens.TokenService;
import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.commands.UpdateUserLeaderIdCommand;
import com.collabrium.iam.authentication.domain.model.commands.UpdateUserMemberIdCommand;
import com.collabrium.iam.authentication.domain.model.commands.SignInCommand;
import com.collabrium.iam.authentication.domain.model.commands.SignUpCommand;
import com.collabrium.iam.authentication.domain.model.events.UserLeaderCreatedEvent;
import com.collabrium.iam.authentication.domain.model.events.UserMemberCreatedEvent;
import com.collabrium.iam.authentication.domain.model.valueobjects.LeaderId;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import com.collabrium.iam.authentication.domain.model.valueobjects.Roles;
import com.collabrium.iam.authentication.domain.services.UserCommandService;
import com.collabrium.iam.authentication.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final TokenService tokenService;
  private final IamEventPublisher iamEventPublisher;

  public UserCommandServiceImpl(RoleRepository roleRepository,
                                UserRepository userRepository,
                                HashingService hashingService,
                                TokenService tokenService,
                                IamEventPublisher iamEventPublisher
  ) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.hashingService = hashingService;
    this.tokenService = tokenService;
    this.iamEventPublisher = iamEventPublisher;
  }

  /**
   * Handle the sign-in command
   * <p>
   *     This method handles the {@link SignInCommand} command and returns the user and the token.
   * </p>
   * @param command the sign-in command containing the username and password
   * @return and optional containing the user matching the username and the generated token
   * @throws RuntimeException if the user is not found or the password is invalid
   */
  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {

    var user = userRepository.findByUsernameWithRoles(command.username());

    if (user.isEmpty())
      throw new RuntimeException("User not found");

    if (!hashingService.matches(command.password(), user.get().getPassword()))
      throw new RuntimeException("Invalid password");

    UserDetailsImpl userDetails = UserDetailsImpl.build(user.get());

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities()
    );

    var token = tokenService.generateToken(authentication);

    return Optional.of(ImmutablePair.of(user.get(), token));
  }

  /**
   * Handle the sign-up command
   * <p>
   *     This method handles the {@link SignUpCommand} command and returns the user.
   * </p>
   * @param command the sign-up command containing the username and password
   * @return the created user
   */
  @Override
  public Optional<User> handle(SignUpCommand command) {

    if (userRepository.existsByUsername(command.username()))
      throw new RuntimeException("Username already exists");

    if(userRepository.existsByEmail(command.email()))
      throw new RuntimeException("User with this email already exists");

    var roles = command.roles().stream()
        .map(role ->
            roleRepository.findByName(role.getName())
                .orElseThrow(() -> new RuntimeException("Role name not found")))
        .toList();

    var user = new User(
        command.username(),
        command.name(),
        command.surname(),
        command.imgUrl(),
        command.email(),
        hashingService.encode(command.password()),
        roles);

    userRepository.save(user);

    var savedUser = userRepository.findByUsernameWithRoles(command.username())
        .orElseThrow();

    publishDomainEvents(savedUser);

    return Optional.of(savedUser);
  }

  @Override
  public void handle(UpdateUserLeaderIdCommand command) {

    updateUser(
        command.userId(),
        user -> user.setLeaderId(
            new LeaderId(command.leaderId())
        )
    );
  }

  @Override
  public void handle(UpdateUserMemberIdCommand command) {

    updateUser(
        command.userId(),
        user -> user.setMemberId(
            new MemberId(command.memberId())
        )
    );
  }

  private void publishDomainEvents(User user) {

    boolean isLeader = user.getRoles().stream()
        .anyMatch(r -> r.getName() == Roles.ROLE_LEADER);

    if (isLeader) {
      iamEventPublisher.publishUserLeaderCreated(
          new UserLeaderCreatedEvent(user.getId())
      );
    }

    boolean isMember = user.getRoles().stream()
        .anyMatch(r -> r.getName() == Roles.ROLE_MEMBER);

    if (isMember) {
      iamEventPublisher.publishUserMemberCreated(
          new UserMemberCreatedEvent(user.getId())
      );
    }
  }

  private void updateUser(
      Long userId,
      Consumer<User> updater
  ) {

    var user = userRepository.findById(userId)
        .orElseThrow(() ->
            new RuntimeException("User not found"));

    updater.accept(user);

    userRepository.save(user);
  }
}