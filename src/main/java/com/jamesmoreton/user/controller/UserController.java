package com.jamesmoreton.user.controller;

import com.jamesmoreton.user.model.User;
import com.jamesmoreton.user.model.UserCreateRequest;
import com.jamesmoreton.user.model.UserType;
import com.jamesmoreton.user.model.UserUpdateRequest;
import com.jamesmoreton.user.model.Users;
import com.jamesmoreton.user.service.UserService;
import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  UUID createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
    return userService.createUser(userCreateRequest);
  }

  @PutMapping(path = {"{userUid}"}, consumes = APPLICATION_JSON_VALUE)
  void updateUser(@PathVariable("userUid") UUID userUid, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
    userService.updateUser(userUid, userUpdateRequest);
  }

  @DeleteMapping(path = {"{userUid}"})
  void deleteUser(@PathVariable("userUid") UUID userUid) {
    userService.deleteUser(userUid);
  }

  @GetMapping(path = {"{userUid}"}, produces = APPLICATION_JSON_VALUE)
  Optional<User> getUser(@PathVariable("userUid") UUID userUid) {
    return userService.getUser(userUid);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  Users getUsers(@RequestParam(value = "userType", required = false) UserType userType, @RequestParam(value = "countryCode", required = false) CountryCode countryCode) {
    return userService.getUsers(userType, countryCode);
  }
}
