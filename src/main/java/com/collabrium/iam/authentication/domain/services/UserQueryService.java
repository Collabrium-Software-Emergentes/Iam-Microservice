package com.collabrium.iam.authentication.domain.services;

import com.collabrium.iam.authentication.domain.model.aggregates.User;
import com.collabrium.iam.authentication.domain.model.queries.*;

import java.util.Optional;

public interface UserQueryService {

  Optional<User> handle(GetUserOnlyByIdQuery query);

  Optional<User> handle(GetUserByMemberIdQuery query);
}