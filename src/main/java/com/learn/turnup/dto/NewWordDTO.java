package com.learn.turnup.dto;

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
 * NewWordDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-02T23:32:11.252341600+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
public class NewWordDTO {

  private String question;

  private String answer;

  public NewWordDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public NewWordDTO(String question, String answer) {
    this.question = question;
    this.answer = answer;
  }

  public NewWordDTO question(String question) {
    this.question = question;
    return this;
  }

  /**
   * Get question
   * @return question
   */
  @NotNull @Pattern(regexp = "^[\\p{L}0-9 ]+$") @Size(min = 1, max = 40)
  @Schema(name = "question", example = "1 black dog", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("question")
  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public NewWordDTO answer(String answer) {
    this.answer = answer;
    return this;
  }

  /**
   * Get answer
   * @return answer
   */
  @NotNull @Pattern(regexp = "^[\\p{L}0-9 ]+$") @Size(min = 1, max = 40)
  @Schema(name = "answer", example = "1 černý pes", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("answer")
  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewWordDTO newWordDTO = (NewWordDTO) o;
    return Objects.equals(this.question, newWordDTO.question) &&
        Objects.equals(this.answer, newWordDTO.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, answer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewWordDTO {\n");
    sb.append("    question: ").append(toIndentedString(question)).append("\n");
    sb.append("    answer: ").append(toIndentedString(answer)).append("\n");
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

