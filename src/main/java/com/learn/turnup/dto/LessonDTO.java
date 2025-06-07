package com.learn.turnup.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * LessonDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T10:45:16.129411600+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class LessonDTO {

  private UUID id;

  private String lessonName;

  private String description;

  private Boolean shared;

  public LessonDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LessonDTO(UUID id, String lessonName, String description, Boolean shared) {
    this.id = id;
    this.lessonName = lessonName;
    this.description = description;
    this.shared = shared;
  }

  public LessonDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LessonDTO lessonName(String lessonName) {
    this.lessonName = lessonName;
    return this;
  }

  /**
   * Get lessonName
   * @return lessonName
   */
  @NotNull @Pattern(regexp = "^[\\p{L} ]+$") @Size(min = 3, max = 30) 
  @Schema(name = "lessonName", example = "Podstatná jména", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lessonName")
  public String getLessonName() {
    return lessonName;
  }

  public void setLessonName(String lessonName) {
    this.lessonName = lessonName;
  }

  public LessonDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull @Pattern(regexp = "^[\\p{L} ]+$") @Size(min = 3, max = 60) 
  @Schema(name = "description", example = "V lekci jsou běžná podstatná jména", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LessonDTO shared(Boolean shared) {
    this.shared = shared;
    return this;
  }

  /**
   * Get shared
   * @return shared
   */
  @NotNull 
  @Schema(name = "shared", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shared")
  public Boolean getShared() {
    return shared;
  }

  public void setShared(Boolean shared) {
    this.shared = shared;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LessonDTO lessonDTO = (LessonDTO) o;
    return Objects.equals(this.id, lessonDTO.id) &&
        Objects.equals(this.lessonName, lessonDTO.lessonName) &&
        Objects.equals(this.description, lessonDTO.description) &&
        Objects.equals(this.shared, lessonDTO.shared);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lessonName, description, shared);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LessonDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lessonName: ").append(toIndentedString(lessonName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    shared: ").append(toIndentedString(shared)).append("\n");
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

