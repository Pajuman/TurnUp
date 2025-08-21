package com.learn.turnup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * UpdateUserRequest
 */

@JsonTypeName("updateUser_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-05T10:53:27.471917+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class UpdateUserRequest {

  private String appUserName;

  private String password;

  private String currentPassword;

  public UpdateUserRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateUserRequest(String appUserName, String password, String currentPassword) {
    this.appUserName = appUserName;
    this.password = password;
    this.currentPassword = currentPassword;
  }

  public UpdateUserRequest appUserName(String appUserName) {
    this.appUserName = appUserName;
    return this;
  }

  /**
   * Get appUserName
   * @return appUserName
   */
  @NotNull @Pattern(regexp = "^[a-zA-Z0-9_]+$") @Size(min = 3, max = 20) 
  @Schema(name = "appUserName", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("appUserName")
  public String getAppUserName() {
    return appUserName;
  }

  public void setAppUserName(String appUserName) {
    this.appUserName = appUserName;
  }

  public UpdateUserRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   */
  @NotNull @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$") 
  @Schema(name = "password", example = "P@ssword123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UpdateUserRequest currentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
    return this;
  }

  /**
   * Original password for authorization
   * @return currentPassword
   */
  @NotNull 
  @Schema(name = "currentPassword", description = "Original password for authorization", requiredMode = Schema.RequiredMode.REQUIRED)
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
    UpdateUserRequest updateUserRequest = (UpdateUserRequest) o;
    return Objects.equals(this.appUserName, updateUserRequest.appUserName) &&
        Objects.equals(this.password, updateUserRequest.password) &&
        Objects.equals(this.currentPassword, updateUserRequest.currentPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appUserName, password, currentPassword);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateUserRequest {\n");
    sb.append("    appUserName: ").append(toIndentedString(appUserName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

