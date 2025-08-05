package com.learn.turnup.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DeleteUserRequest
 */

@JsonTypeName("deleteUser_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-05T10:53:27.471917+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class DeleteUserRequest {

  private String currentPassword;

  public DeleteUserRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DeleteUserRequest(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  public DeleteUserRequest currentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
    return this;
  }

  /**
   * Current password for confirmation
   * @return currentPassword
   */
  @NotNull 
  @Schema(name = "currentPassword", description = "Current password for confirmation", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currentPassword")
  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteUserRequest deleteUserRequest = (DeleteUserRequest) o;
    return Objects.equals(this.currentPassword, deleteUserRequest.currentPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteUserRequest {\n");
    sb.append("    currentPassword: ").append(toIndentedString(currentPassword)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

