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
 * WordDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T10:45:16.129411600+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class WordDTO {

  private UUID id;

  private String question;

  private String answer;

  private Integer score;

  public WordDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WordDTO(UUID id, String question, String answer, Integer score) {
    this.id = id;
    this.question = question;
    this.answer = answer;
    this.score = score;
  }

  public WordDTO id(UUID id) {
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

  public WordDTO question(String question) {
    this.question = question;
    return this;
  }

  /**
   * Get question
   * @return question
   */
  @NotNull @Pattern(regexp = "^[\\p{L} ]+$") @Size(min = 1, max = 40) 
  @Schema(name = "question", example = "black dog", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("question")
  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public WordDTO answer(String answer) {
    this.answer = answer;
    return this;
  }

  /**
   * Get answer
   * @return answer
   */
  @NotNull @Pattern(regexp = "^[\\p{L} ]+$") @Size(min = 1, max = 40) 
  @Schema(name = "answer", example = "černý pes", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("answer")
  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public WordDTO score(Integer score) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WordDTO wordDTO = (WordDTO) o;
    return Objects.equals(this.id, wordDTO.id) &&
        Objects.equals(this.question, wordDTO.question) &&
        Objects.equals(this.answer, wordDTO.answer) &&
        Objects.equals(this.score, wordDTO.score);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, question, answer, score);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WordDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    question: ").append(toIndentedString(question)).append("\n");
    sb.append("    answer: ").append(toIndentedString(answer)).append("\n");
    sb.append("    score: ").append(toIndentedString(score)).append("\n");
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

