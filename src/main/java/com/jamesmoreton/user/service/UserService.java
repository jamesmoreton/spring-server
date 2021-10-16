package com.jamesmoreton.user.service;

import com.jamesmoreton.exception.RequestValidationException;
import com.jamesmoreton.user.model.User;
import com.jamesmoreton.user.model.UserCreateRequest;
import com.jamesmoreton.user.model.UserType;
import com.jamesmoreton.user.model.UserUpdateRequest;
import com.jamesmoreton.user.model.Users;
import com.neovisionaries.i18n.CountryCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.jamesmoreton.exception.RequestValidationException.ErrorCode.USER_NOT_FOUND;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  // Mock database
  private final Map<UUID, User> userMap = new HashMap<>();

  private final Clock clock;

  @Autowired
  UserService(Clock clock) {
    this.clock = clock;
  }

  public UUID createUser(UserCreateRequest request) {
    final UUID userUid = UUID.randomUUID();
    logger.info("Creating user {}", userUid);

    final User user = new User(
        userUid,
        request.getUserType(),
        request.getDateOfBirth(),
        request.getCountryCode(),
        ZonedDateTime.now(clock)
    );
    userMap.put(userUid, user);
    return userUid;
  }

  public void updateUser(UUID userUid, UserUpdateRequest request) {
    final User user = Optional.ofNullable(userMap.get(userUid))
        .orElseThrow(() ->RequestValidationException.forCode(USER_NOT_FOUND, "User to update not found"));
    logger.info("Updating user {} to {}", userUid, request.getUserType());

    final User updated = new User(
        userUid,
        request.getUserType(),
        user.getDateOfBirth(),
        user.getCountryCode(),
        user.getCreatedAt()
    );
    userMap.put(userUid, updated);
  }

  public void deleteUser(UUID userUid) {
    logger.info("Deleting user {}", userUid);
    userMap.remove(userUid);
  }

  public Optional<User> getUser(UUID userUid) {
    logger.info("Getting user {}", userUid);
    return Optional.ofNullable(userMap.get(userUid));
  }

  public Users getUsers(UserType userType, CountryCode countryCode) {
    logger.info("Getting users with userType={}, countryCode={}", userType, countryCode);
    final List<User> users = userMap.values()
        .stream()
        .filter(u -> userType == null || u.getUserType().equals(userType))
        .filter(u -> countryCode == null || u.getCountryCode().equals(countryCode))
        .collect(Collectors.toList());
    return new Users(users);
  }
}
