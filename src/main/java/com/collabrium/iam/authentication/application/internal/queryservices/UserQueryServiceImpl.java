package com.collabrium.iam.authentication.application.internal.queryservices;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.queries.*;
import com.collabrium.iam.authentication.domain.model.valueobjects.MemberId;
import com.collabrium.iam.authentication.domain.services.UserQueryService;
import com.collabrium.iam.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

  private final UserRepository userRepository;

  /**
   * Constructor.
   *
   * @param userRepository {@link UserRepository} instance.
   */
  public UserQueryServiceImpl(UserRepository userRepository
  ) {

    this.userRepository = userRepository;
  }

  /**
   * This method is used to handle {@link GetUserByIdQuery} query.
   * @param query {@link GetUserByIdQuery} instance.
   * @return {@link Optional} of {@link User} instance.
   * @see GetUserByIdQuery
   */
  @Override
  public Optional<User> handle(GetUserOnlyByIdQuery query) {
    return userRepository.findById(query.userId());
  }

  /**
   * This method is used to handle {@link GetUserByMemberIdQuery} query.
   * @param query {@link GetUserByMemberIdQuery} instance.
   * @return {@link Optional} of {@link User} instance.
   * @see GetUserByMemberIdQuery
   */
  @Override
  public Optional<User> handle(GetUserByMemberIdQuery query) {

    MemberId memberId = new MemberId(query.memberId());

    return userRepository.findByMemberId(memberId);
  }
}