package com.learn.turnup.exceptions;

import java.util.Set;
import java.util.UUID;

public class DefaultUser {
  public static void denyForDefaultUser(UUID xUserId){
    if (xUserId.equals(DEFAULT_USER_ID)) {
      throw new IllegalArgumentException("Not allowed for user Demo.");
    }
  }
  private static final UUID DEFAULT_USER_ID = UUID.fromString("5193a512-1006-4136-b6dd-def65658da9d");
}
