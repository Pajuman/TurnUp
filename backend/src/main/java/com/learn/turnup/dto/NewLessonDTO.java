package com.learn.turnup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * NewLessonDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-05T10:53:27.471917+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class NewLessonDTO {

  private String lessonName;

  private String description;

  private String language;

  private Boolean shared;

  public NewLessonDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public NewLessonDTO(String lessonName, String description, String language, Boolean shared) {
    this.lessonName = lessonName;
    this.description = description;
    this.language = language;
    this.shared = shared;
  }

  public NewLessonDTO lessonName(String lessonName) {
    this.lessonName = lessonName;
    return this;
  }

  /**
   * Get lessonName
   * @return lessonName
   */
  @NotNull @Size(min = 3, max = 30)
  @Schema(name = "lessonName", example = "Podstatná jména", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lessonName")
  public String getLessonName() {
    return lessonName;
  }

  public void setLessonName(String lessonName) {
    this.lessonName = lessonName;
  }

  public NewLessonDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull @Size(min = 2, max = 60)
  @Schema(name = "description", example = "V lekci jsou běžná podstatná jména", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public NewLessonDTO language(String language) {
    this.language = language;
    return this;
  }

  /**
   * Get language
   * @return language
   */
  @NotNull @Pattern(regexp = "^[\\p{L}0-9 ]+$") @Size(min = 2, max = 30) 
  @Schema(name = "language", example = "AJ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public NewLessonDTO shared(Boolean shared) {
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
    NewLessonDTO newLessonDTO = (NewLessonDTO) o;
    return Objects.equals(this.lessonName, newLessonDTO.lessonName) &&
        Objects.equals(this.description, newLessonDTO.description) &&
        Objects.equals(this.language, newLessonDTO.language) &&
        Objects.equals(this.shared, newLessonDTO.shared);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lessonName, description, language, shared);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewLessonDTO {\n");
    sb.append("    lessonName: ").append(toIndentedString(lessonName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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

