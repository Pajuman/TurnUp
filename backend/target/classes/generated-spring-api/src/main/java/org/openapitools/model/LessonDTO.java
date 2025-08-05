package org.openapitools.model;

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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-05T10:53:27.471917+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class LessonDTO {

  private UUID id;

  private String lessonName;

  private String description;

  private String language;

  private Integer score;

  private Integer wordCount;

  private Boolean shared;

  public LessonDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LessonDTO(UUID id, String lessonName, String description, String language, Integer score, Integer wordCount, Boolean shared) {
    this.id = id;
    this.lessonName = lessonName;
    this.description = description;
    this.language = language;
    this.score = score;
    this.wordCount = wordCount;
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
  @NotNull @Pattern(regexp = "^[\\p{L}0-9 ]+$") @Size(min = 2, max = 30) 
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
  @NotNull @Pattern(regexp = "^[\\p{L}0-9 ]+$") @Size(min = 3, max = 60) 
  @Schema(name = "description", example = "V lekci jsou běžná podstatná jména", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LessonDTO language(String language) {
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

  public LessonDTO score(Integer score) {
    this.score = score;
    return this;
  }

  /**
   * Get score
   * @return score
   */
  @NotNull 
  @Schema(name = "score", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("score")
  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public LessonDTO wordCount(Integer wordCount) {
    this.wordCount = wordCount;
    return this;
  }

  /**
   * Get wordCount
   * @return wordCount
   */
  @NotNull 
  @Schema(name = "wordCount", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("wordCount")
  public Integer getWordCount() {
    return wordCount;
  }

  public void setWordCount(Integer wordCount) {
    this.wordCount = wordCount;
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
        Objects.equals(this.language, lessonDTO.language) &&
        Objects.equals(this.score, lessonDTO.score) &&
        Objects.equals(this.wordCount, lessonDTO.wordCount) &&
        Objects.equals(this.shared, lessonDTO.shared);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lessonName, description, language, score, wordCount, shared);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LessonDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lessonName: ").append(toIndentedString(lessonName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
    sb.append("    wordCount: ").append(toIndentedString(wordCount)).append("\n");
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

