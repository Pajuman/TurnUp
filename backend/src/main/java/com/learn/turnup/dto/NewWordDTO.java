package com.learn.turnup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/**
 * NewWordDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-05T10:53:27.471917+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
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
  @NotNull @Size(min = 1, max = 40)
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
  @NotNull @Size(min = 1, max = 40)
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

