package com.learn.turnup.exceptions;

import java.util.Set;
import java.util.UUID;

public class DefaultUser {
  public static void denyForDefaultUser(UUID xUserId){
    if (xUserId.equals(DEFAULT_USER_ID)) {
      throw new IllegalArgumentException("Not allowed for user Demo.");
    }
  }
  private static final UUID DEFAULT_USER_ID = UUID.fromString("9f81e4f8-9d8c-44c4-ae35-eb07acd58d4a");
}
