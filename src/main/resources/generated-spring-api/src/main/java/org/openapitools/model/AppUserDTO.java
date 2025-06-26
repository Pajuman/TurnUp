package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AppUserDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-10T10:26:27.055127200+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class AppUserDTO {

  private String appUserName;

  private String passwordHash;

  public AppUserDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AppUserDTO(String appUserName, String passwordHash) {
    this.appUserName = appUserName;
    this.passwordHash = passwordHash;
  }

  public AppUserDTO appUserName(String appUserName) {
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

  public AppUserDTO passwordHash(String passwordHash) {
    this.passwordHash = passwordHash;
    return this;
  }

  /**
   * Get passwordHash
   * @return passwordHash
   */
  @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]+$") @Size(min = 8, max = 16) 
  @Schema(name = "passwordHash", example = "P@ssw0rd123", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("passwordHash")
  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppUserDTO appUserDTO = (AppUserDTO) o;
    return Objects.equals(this.appUserName, appUserDTO.appUserName) &&
        Objects.equals(this.passwordHash, appUserDTO.passwordHash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appUserName, passwordHash);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppUserDTO {\n");
    sb.append("    appUserName: ").append(toIndentedString(appUserName)).append("\n");
    sb.append("    passwordHash: ").append(toIndentedString(passwordHash)).append("\n");
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

